package com.scor.rr.mapper;

import com.scor.rr.domain.GlobalViewSummary;
import com.scor.rr.domain.riskLink.RLExposureSummaryItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RLExposureSummaryItemRowMapper implements RowMapper<RLExposureSummaryItem> {
    private GlobalViewSummary globalViewSummary;

    public RLExposureSummaryItemRowMapper(GlobalViewSummary globalViewSummaryId)
    {
        this.globalViewSummary = globalViewSummaryId;
    }

    @Override
    public RLExposureSummaryItem mapRow(ResultSet rs, int line) throws SQLException
    {
        // FIXME : is the "Analysis Region" is to be used at all or is it a specification artifact no longer used ?
        RLExposureSummaryItem tmp=new RLExposureSummaryItem();
        //
        tmp.setGlobalViewSummary(globalViewSummary);
        tmp.setPortfolioId(rs.getLong("port_id"));
        tmp.setPortfolioType(rs.getString("port_type"));
        tmp.setExposureSummaryName(rs.getString("SummaryName"));
        //
        tmp.setFinancialPerspective(rs.getString("FinancialPerspective"));
        tmp.setPeril(rs.getString("Peril"));
        // NOTA trim the admin as the procedure can return white space.
        tmp.setAdmin1Code(StringUtils.trim(rs.getString("Admin1Code")));
        tmp.setCountryCode(rs.getString("CountryCode"));
        //
        tmp.setDimension1(rs.getString("DimensionValue1"));
        tmp.setDimension2(rs.getString("DimensionValue2"));
        tmp.setDimension3(rs.getString("DimensionValue3"));
        tmp.setDimension4(rs.getString("DimensionValue4"));
        tmp.setDimensionSort1(rs.getInt("DimensionSort1"));
        tmp.setDimensionSort2(rs.getInt("DimensionSort2"));
        tmp.setDimensionSort3(rs.getInt("DimensionSort3"));
        tmp.setDimensionSort4(rs.getInt("DimensionSort4"));
        //
        tmp.setLocationCount(rs.getLong("LocCount"));
        tmp.setTotalTiv(rs.getDouble("TotalTIV"));
        //
        tmp.setExposureCurrency(rs.getString("Expo_Ccy"));
        tmp.setExposureCurrencyUSDRate(rs.getDouble("Expo_Ccy_Roe_USD"));
        tmp.setConformedCurrency(rs.getString("Conformed_Ccy"));
        tmp.setConformedCurrencyUSDRate(rs.getDouble("Conformed_Ccy_ROE_USD"));
        java.sql.Date roe=rs.getDate("RoE_AsAtDate");
        if(roe!=null) {
            tmp.setRateDate(new Date(roe.getTime()));
        }
        //
        return tmp;
    }
}
