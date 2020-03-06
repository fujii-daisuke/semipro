package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.PlaceArrangement;

public class PlaceArrangementTypeHandler extends BaseTypeHandler<PlaceArrangement> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i,
        PlaceArrangement placeArrangement, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, placeArrangement.getValue());
    }

    @Override
    public PlaceArrangement getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(PlaceArrangement::getPlaceArrangement).orElse(null);
    }

    @Override
    public PlaceArrangement getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(PlaceArrangement::getPlaceArrangement).orElse(null);
    }

    @Override
    public PlaceArrangement getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(PlaceArrangement::getPlaceArrangement).orElse(null);
    }
}
