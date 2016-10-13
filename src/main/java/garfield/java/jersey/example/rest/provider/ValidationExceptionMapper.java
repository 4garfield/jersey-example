package garfield.java.jersey.example.rest.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.validation.ValidationError;

/**
 * Commonly, if throw ConstraintViolationException, then may return 400/500. But we just return 400 to prevent complicated code to get status code.
 * If the exception is an instance of ConstraintViolationException we send a list of ValidationError instances in the response.
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    
    @Override
    public Response toResponse(final ValidationException exception) {
        
        if (exception instanceof ConstraintViolationException) {
            final ConstraintViolationException cve = (ConstraintViolationException) exception;
            return Response.status(Status.BAD_REQUEST).entity(getEntity(cve.getConstraintViolations())).build();
        } else { 
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
        }
    }
    
    private List<ValidationError> getEntity(final Set<ConstraintViolation<?>> violations) {
        final List<ValidationError> errors = new ArrayList<ValidationError>();
        for (final ConstraintViolation<?> violation : violations) {
            errors.add(new ValidationError(String.valueOf(violation.getInvalidValue()), violation.getMessage(), violation.getMessageTemplate(), getPath(violation)));
        }
        return errors;
    }
    
    private String getPath(final ConstraintViolation<?> violation) {
        final String propertyPath = violation.getPropertyPath().toString();
        return violation.getLeafBean().getClass().getSimpleName() + (!"".equals(propertyPath) ? '.' + propertyPath : "");
    }
}
