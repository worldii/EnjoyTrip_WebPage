package com.ssafy.enjoytrip.core.board.model.typeHandler;

import com.ssafy.enjoytrip.core.board.model.entity.BoardType;
import java.sql.ResultSet;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(BoardType.class)
public class BoardTypeHandler implements TypeHandler<BoardType> {

    @Override
    public void setParameter(java.sql.PreparedStatement ps, int i, BoardType parameter,
        org.apache.ibatis.type.JdbcType jdbcType) throws java.sql.SQLException {
        ps.setString(i, parameter.getType());
    }

    @Override
    public BoardType getResult(java.sql.ResultSet rs, String columnName)
        throws java.sql.SQLException {
        String typeKey = rs.getString(columnName);
        return getBoardType(typeKey);
    }

    @Override
    public BoardType getResult(ResultSet rs, int columnIndex)
        throws java.sql.SQLException {
        String typeKey = rs.getString(columnIndex);
        return getBoardType(typeKey);
    }

    @Override
    public BoardType getResult(java.sql.CallableStatement cs, int columnIndex)
        throws java.sql.SQLException {
        String typeKey = cs.getString(columnIndex);
        return getBoardType(typeKey);
    }

    private BoardType getBoardType(String type) {
        return BoardType.valueOf(type.toUpperCase());
    }

}
