package red.semipro.domain.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import red.semipro.domain.enums.PaymentType;

public class PaymentTypeTypeHandler extends BaseTypeHandler<PaymentType> {

    @Override
    public PaymentType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return PaymentType.valueOf(rs.getInt(columnName));
    }

    @Override
    public PaymentType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return PaymentType.valueOf(rs.getInt(columnIndex));
    }

    @Override
    public PaymentType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return PaymentType.valueOf(cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PaymentType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
        
    }

}
