package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import red.semipro.domain.enums.BusinessType;

public class BusinessTypeTypeHandler extends BaseTypeHandler<BusinessType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BusinessType parameter,
        JdbcType jdbcType)
        throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public BusinessType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getString(columnName)).map(BusinessType::getBusinessType).orElse(null);
    }

    @Override
    public BusinessType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getString(columnIndex)).map(BusinessType::getBusinessType).orElse(null);
    }

    @Override
    public BusinessType getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
        return Optional.ofNullable(cs.getString(columnIndex)).map(BusinessType::getBusinessType).orElse(null);
    }
}
