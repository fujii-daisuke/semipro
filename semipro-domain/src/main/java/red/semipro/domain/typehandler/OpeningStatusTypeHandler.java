package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import red.semipro.domain.enums.OpeningStatus;

public class OpeningStatusTypeHandler extends BaseTypeHandler<OpeningStatus> {

    @Override
    public OpeningStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return OpeningStatus.valueOf(rs.getInt(columnName));
    }

    @Override
    public OpeningStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return OpeningStatus.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public OpeningStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return OpeningStatus.valueOf(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OpeningStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
        
    }

}
