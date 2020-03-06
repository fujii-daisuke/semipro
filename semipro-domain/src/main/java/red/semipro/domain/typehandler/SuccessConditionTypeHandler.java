package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.SuccessCondition;

public class SuccessConditionTypeHandler extends BaseTypeHandler<SuccessCondition> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i,
        SuccessCondition successCondition, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, successCondition.getValue());
    }

    @Override
    public SuccessCondition getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(SuccessCondition::getSuccessCondition).orElse(null);
    }

    @Override
    public SuccessCondition getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(SuccessCondition::getSuccessCondition).orElse(null);
    }

    @Override
    public SuccessCondition getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(SuccessCondition::getSuccessCondition).orElse(null);
    }
}
