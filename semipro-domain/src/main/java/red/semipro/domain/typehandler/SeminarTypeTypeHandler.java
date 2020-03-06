package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.SeminarType;

public class SeminarTypeTypeHandler extends BaseTypeHandler<SeminarType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SeminarType parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public SeminarType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(SeminarType::getSeminarType).orElse(null);
    }

    @Override
    public SeminarType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(SeminarType::getSeminarType).orElse(null);
    }

    @Override
    public SeminarType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(SeminarType::getSeminarType).orElse(null);
    }
}
