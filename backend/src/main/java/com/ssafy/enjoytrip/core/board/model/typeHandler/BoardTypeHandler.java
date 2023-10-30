package com.ssafy.enjoytrip.core.board.model.typeHandler;


import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(BoardType.class)
public class BoardTypeHandler implements TypeHandler<BoardType> {

    @Override
    public void setParameter(
            final PreparedStatement ps,
            final int parameterIndex,
            final BoardType parameter,
            final JdbcType jdbcType)
            throws java.sql.SQLException {
        ps.setString(parameterIndex, parameter.getType());
    }

    @Override
    public BoardType getResult(final ResultSet rs, final String columnName) throws SQLException {
        return getBoardType(rs.getString(columnName));
    }

    @Override
    public BoardType getResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return getBoardType(rs.getString(columnIndex));
    }

    @Override
    public BoardType getResult(final CallableStatement cs, final int columnIndex)
            throws SQLException {
        return getBoardType(cs.getString(columnIndex));
    }

    private BoardType getBoardType(final String type) {
        return BoardType.valueOf(type.toUpperCase());
    }
}
