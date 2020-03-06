package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.OpeningStatus;

public class OpeningStatusTypeHandler extends BaseTypeHandler<OpeningStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OpeningStatus parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public OpeningStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(OpeningStatus::getOpeningStatus).orElse(null);
    }

    @Override
    public OpeningStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(OpeningStatus::getOpeningStatus).orElse(null);
    }

    @Override
    public OpeningStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(OpeningStatus::getOpeningStatus).orElse(null);
    }

}
