package red.semipro.share.dialect;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;
import red.semipro.domain.aws.service.SeminarImageService;

@Component
@RequiredArgsConstructor
public class SemiproExpressionObjectFactory implements IExpressionObjectFactory {

    private static final String seminarImageExpression = "seminarImageHelper";

    private final SeminarImageService seminarImageService;

    private static final Set<String> expressionSet = new HashSet<String>() {
        {
            add(seminarImageExpression);
        }
    };

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return expressionSet;
    }

    @Override
    public Object buildObject(IExpressionContext iExpressionContext, String expressionObjectName) {
        switch (expressionObjectName) {
            case seminarImageExpression:
                return seminarImageService;
            default:
                return null;
        }
    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return false;
    }
}
