package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.RegisterStatus;

public class RegisterStatusTypeHandler extends BaseTypeHandler<RegisterStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RegisterStatus parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public RegisterStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(RegisterStatus::getRegisterStatus).orElse(null);
    }

    @Override
    public RegisterStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(RegisterStatus::getRegisterStatus).orElse(null);
    }

    @Override
    public RegisterStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(RegisterStatus::getRegisterStatus).orElse(null);
    }
}
