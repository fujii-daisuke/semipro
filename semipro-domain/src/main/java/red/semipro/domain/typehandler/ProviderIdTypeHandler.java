package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import red.semipro.domain.enums.ProviderId;

public class ProviderIdTypeHandler extends BaseTypeHandler<ProviderId> {

    @Override
    public ProviderId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ProviderId.valueOf(rs.getInt(columnName));
    }

    @Override
    public ProviderId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ProviderId.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public ProviderId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ProviderId.valueOf(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ProviderId parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
        
    }

}
