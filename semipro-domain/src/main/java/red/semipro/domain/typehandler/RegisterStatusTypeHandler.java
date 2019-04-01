package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import red.semipro.domain.enums.RegisterStatus;

public class RegisterStatusTypeHandler extends BaseTypeHandler<RegisterStatus> {

    @Override
    public RegisterStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return RegisterStatus.valueOf(rs.getInt(columnName));
    }

    @Override
    public RegisterStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return RegisterStatus.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public RegisterStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return RegisterStatus.valueOf(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RegisterStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
        
    }

}
