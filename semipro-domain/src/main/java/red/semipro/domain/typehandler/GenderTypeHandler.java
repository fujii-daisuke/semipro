package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.Gender;

public class GenderTypeHandler extends BaseTypeHandler<Gender> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter,
        JdbcType jdbcType)
        throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(Gender::getGender).orElse(null);
    }

    @Override
    public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(Gender::getGender).orElse(null);
    }

    @Override
    public Gender getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(Gender::getGender).orElse(null);

    }
}
