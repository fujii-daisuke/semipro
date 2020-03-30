package red.semipro.share.dialect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

@Component
@RequiredArgsConstructor
public class SemiproExpressionObjectDialect implements IExpressionObjectDialect {

    private final SemiproExpressionObjectFactory semiproExpressionObjectFactory;

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return semiproExpressionObjectFactory;
    }

    @Override
    public String getName() {
        return "SemiproExpressionObjectDialect";
    }
}
