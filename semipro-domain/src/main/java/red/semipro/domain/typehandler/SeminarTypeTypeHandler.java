package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import red.semipro.domain.enums.SeminarType;

public class SeminarTypeTypeHandler extends BaseTypeHandler<SeminarType> {

    @Override
    public SeminarType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return SeminarType.valueOf(rs.getInt(columnName));
    }

    @Override
    public SeminarType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return SeminarType.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public SeminarType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return SeminarType.valueOf(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SeminarType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

}
