package com.scor.rr.util;

public class EmbeddedQueries {

    public static final String TIV_QUERY = "SELECT\tRegionPerilCode as RegionPerilCode\n" +
            "\t\t\t,\tSUM( tiv.LocCount ) AS LocCount\n" +
            "\t\t\t,\tSUM( tiv.TotalTiv ) AS TotalTIV\n" +
            "\t\tFROM\t(\n" +
            "\t\t\t\tSELECT\tDISTINCT\n" +
            "\t\t\t\t\t\tcoalesce( map.RegionPerilCode , map2.RegionPerilCode) as RegionPerilCode\n" +
            "\t\t\t\t\t,\ttiv.*\n" +
            "\t\t\t\tFROM\t(\n" +
            "\t\t\t\t\t\t----------------------------------------------------------------------\n" +
            "\t\t\t\t\t\t-- NEW RAW REGIION PERIL LEVEL TIV & LOC COUNT EXTRACT\n" +
            "\t\t\t\t\t\t----------------------------------------------------------------------\n" +
            "\t\t\t\t\t\tSELECT  CONVERT( VARCHAR( 1000 ), t.CountryCode )   AS CountryCode\n" +
            "\t\t\t\t\t\t\t ,  CONVERT( VARCHAR( 1000 ), t.AdminCode1 )    AS Admin1Code\n" +
            "\t\t\t\t\t\t\t ,  CONVERT( VARCHAR( 1000 ), t.Peril )         AS Peril\n" +
            "\t\t\t\t\t\t\t ,  SUM( t.TIV_LIMITCCY )                       AS TotalTIV\n" +
            "\t\t\t\t\t\t\t ,  COUNT( DISTINCT LOCNUM )                    AS LocCount\n" +
            "\t\t\t\t\t\tFROM    (\n" +
            "\t\t\t\t\t\t\t\tSELECT CASE WHEN adr.CountryRMSCode = 'CB'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t THEN adr.CountryCode\n" +
            "\t\t\t\t\t\t\t\t\t\t\t ELSE adr.CountryRMSCode\n" +
            "\t\t\t\t\t\t\t\t\t\tEND\t\t\t\t\t\t\t\t\tAS CountryCode\n" +
            "\t\t\t\t\t\t\t\t\t ,\tadr.Admin1Code\t\t\t\t\t\tAS AdminCode1\n" +
            "\t\t\t\t\t\t\t\t\t ,  lcv.PERIL\n" +
            "\t\t\t\t\t\t\t\t\t ,  ppt.LOCNUM\n" +
            "\t\t\t\t\t\t\t\t\t ,  ppt.LOCNAME\n" +
            "\t\t\t\t\t\t\t\t\t ,  limfx.CODE                          AS CcyCode\n" +
            "\t\t\t\t\t\t\t\t\t ,\n" +
            "\t\t\t\t\t\t\t\t\t  MAX\n" +
            "\t\t\t\t\t\t\t\t\t\t( lcv.VALUEAMT\n" +
            "\t\t\t\t\t\t\t\t\t\t   * (LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "\t\t\t\t\t\t\t\t\t\t   )\t\t\t\t\t\t\t\tAS TIV_LIMITCCY\n" +
            "\t\t\t\t\t\t\t\tFROM    :edm:.dbo.PortInfo poi\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.PortAcct pra\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  pra.PORTINFOID = poi.PORTINFOID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.Property ppt\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  ppt.ACCGRPID   = pra.ACCGRPID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.Address adr\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  adr.AddressID  = ppt.AddressID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t   SELECT  lcv.LocID , lcv.Peril, lcv.ValueCur\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t,  SUM( lcv.VALUEAMT )     AS VALUEAMT\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t   FROM    :edm:.dbo.loccvg lcv\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t   GROUP BY\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t   lcv.LOCID , lcv.PERIL, lcv.ValueCur\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  ) lcv\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON lcv.LOCID   = ppt.LOCID\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN rms_userconfig.dbo.currfx LocFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  LocFX.CODE     = lcv.VALUECUR\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tAND LocFX.XDATE    = (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t SELECT  MAX( XDATE )\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t FROM    rms_userconfig.dbo.currfx LocFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE   CODE = 'USD'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t )\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN rms_userconfig.dbo.currfx LimFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  LimFX.CODE     = ?\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tAND LimFX.XDATE    = (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t SELECT  MAX( XDATE )\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t FROM    rms_userconfig.dbo.currfx LocFX\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t WHERE   CODE = 'USD'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t )\n" +
            "\t\t\t\t\t\t\t\tWHERE   poi.PORTNUM = ?\n" +
            "\t\t\t\t\t\t\t\tAND\t\tlcv.PERIL < 5\n" +
            "\t\t\t\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t\t\t\t\tCASE WHEN adr.CountryRMSCode = 'CB'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t THEN adr.CountryCode\n" +
            "\t\t\t\t\t\t\t\t\t\t\t ELSE adr.CountryRMSCode\n" +
            "\t\t\t\t\t\t\t\t\t\tEND\n" +
            "\t\t\t\t\t\t\t\t\t,\tadr.Admin1Code\n" +
            "\t\t\t\t\t\t\t\t\t,   lcv.PERIL\n" +
            "\t\t\t\t\t\t\t\t\t,   ppt.LOCNUM\n" +
            "\t\t\t\t\t\t\t\t\t,   ppt.LOCNAME\n" +
            "\t\t\t\t\t\t\t\t\t,   limfx.CODE\n" +
            "\t\t\t\t\t\t\t\t) t\n" +
            "\t\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t\t\tCONVERT( VARCHAR( 1000 ),  t.CountryCode  )\n" +
            "\t\t\t\t\t\t\t,\tCONVERT( VARCHAR( 1000 ),  t.AdminCode1  )\n" +
            "\t\t\t\t\t\t\t,   CONVERT( VARCHAR( 1000 ), t.Peril )\n" +
            "\t\t\t\t\t\t) tiv\n" +
            "\t\t\t\t\tLEFT  JOIN SCOR_REFERENCE.dbo.RegionPerilMapping map\n" +
            "\t\t\t\t\t\t\tON\tmap.SourceVendor\t\t= 'RMS'\n" +
            "\t\t\t\t\t\t\tAND\tmap.PerilCode\t\t\t= tiv.Peril\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map.CountryCode)  = RTRIM(tiv.CountryCode)\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map.Admin1Code)   = RTRIM(tiv.Admin1Code)\n" +
            "\t\t\t\t\tLEFT  JOIN SCOR_REFERENCE.dbo.RegionPerilMapping map2\n" +
            "\t\t\t\t\t\t\tON\tmap2.SourceVendor\t\t= 'RMS'\n" +
            "\t\t\t\t\t\t\tAND\tmap2.PerilCode\t\t\t= RTRIM(Convert(Varchar(10),tiv.Peril))\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map2.CountryCode) = RTRIM(tiv.CountryCode)\n" +
            "\t\t\t\t\t\t\tAND RTRIM(map2.Admin1Code)  = ''\n" +
            "\t\t\t\t) tiv\n" +
            "\t\tGROUP BY\n" +
            "\t\t\t\tRegionPerilCode";

    public static final String RL_ACC_QUERY = "---------------------------------------------------------------------\n" +
            "---\n" +
            "---  FW ACC Exposure Extract (With Region Peril workaround)\n" +
            "---\n" +
            "---------------------------------------------------------------------\n" +
            "---     29.06.2015  - Fix to address issue with Cat Sub limits\n" +
            "---------------------------------------------------------------------\n" +
            "SELECT\n" +
            "            res.CAR_ID\n" +
            "      ,     res.Division\n" +
            "      ,     Res.InceptionDate\n" +
            "      ,     res.PracticalCompletionDate\n" +
            "      ,     Coalesce( rp1.RegionPerilCode, rp2.RegionPerilCode ) AS RegionPerilCode\n" +
            "      ,     MAX( res.CatDeductible )\n" +
            "      ,     MAX( res.CatSubLimit ) CatSubLimit\n" +
            "FROM  (\n" +
            "                  SELECT  t.CAR_ID\n" +
            "                        ,   t.Division\n" +
            "                        ,   t.CountryCode\n" +
            "                        ,   t.LocationGroupCode\n" +
            "                        ,   t.Peril\n" +
            "                        ,   t.CatDeductible\n" +
            "                        ,   t.CatSubLimit\n" +
            "                        ,   t.InceptionDate\n" +
            "                        ,   t.PracticalCompletionDate\n" +
            "                        FROM    (\n" +
            "                                          SELECT  acg.CAR_ID\n" +
            "                                                ,   acg.Division\n" +
            "                                                ,   acg.UWYear\n" +
            "                                                ,   acg.CountryCode\n" +
            "                                                ,   acg.LocationGroupCode\n" +
            "                                                ,   acg.Peril\n" +
            "                                                ,   pol.CatDeductible\n" +
            "                                                ,   pol.CatDeductibleCcy\n" +
            "                                                ,   pol.CatSubLimit\n" +
            "                                                ,   pol.CatSubLimitCcy\n" +
            "                                                ,   pol.InceptionDate\n" +
            "                                                ,   pol.PracticalCompletionDate\n" +
            "                                          FROM    :edm:.dbo.PortInfo poi\n" +
            "                                                      INNER JOIN :edm:.dbo.portacct pa\n" +
            "                                                                  ON  pa.PORTINFOID = poi.PORTINFOID\n" +
            "                                                      INNER JOIN (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    \t\t-------------------------------------------------------------\n" +
            "                                                                        -- Query to collate the various country, peril and admin1\n" +
            "                                                                        -------------------------------------------------------------\n" +
            "                                                                        SELECT    acg.USERID4                      AS CAR_ID\n" +
            "                                                                              ,   acg.AccGrpID\n" +
            "                                                                              ,\t  ACG.ACCGRPNUM\n" +
            "                                                                              ,   SUBSTRING( acg.ACCGRPNUM,  1, 9) AS PolicyNum\n" +
            "                                                                              ,   SUBSTRING( acg.ACCGRPNUM, 10, 2) AS Division\n" +
            "                                                                              ,   SUBSTRING( acg.ACCGRPNUM, 12, 4) AS UWYear\n" +
            "                                                                              ,   prl.Peril\n" +
            "                                                                              ,   CASE WHEN  adr.CountryRMSCode = 'CB'\n" +
            "                                                                                          THEN  adr.CountryCode\n" +
            "                                                                                           ELSE  adr.CountryRMSCode\n" +
            "                                                                                    END as CountryCode\n" +
            "                                                                              ,   adr.Admin1Code                   as LocationGroupCode\n" +
            "                                                                              ,\tppt.LOCID\n" +
            "                                                                              ,\tppt.LOCNUM, ppt.LOCNAME\n" +
            "                                                                              ,\tcvg.TotalExposureAmt\n" +
            "                                                                        FROM\t:edm:.dbo.accgrp acg\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.Property  ppt\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tON  ppt.ACCGRPID = acg.AccGrpID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tINNER JOIN :edm:.dbo.Address adr\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tON  ppt.AddressID = adr.AddressID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tINNER JOIN ( SELECT DISTINCT\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  po.ACCGRPID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t,     SUBSTRING( Po.PolicyNum, 18, 2)           AS Peril\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  FROM   :edm:.dbo.policy po\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   ) prl\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tON    prl.AccGrpID = acg.AccGrpID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tINNER JOIN (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSELECT\tLocID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t, \tCASE Peril\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 1 THEN 'EQ'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 2 THEN 'WS'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHEN 3 THEN 'CS'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tELSE 'FL'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tEND AS Peril\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t,\tSUM(ValueAmt) AS TotalExposureAmt\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM\t:edm:.dbo.loccvg cvg\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE\tPeril < 5\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLOCID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t,\tPeril\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tHAVING\tSUM(cvg.VALUEAMT) > 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   ) cvg\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tON  cvg.LOCID = ppt.LOCID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND cvg.Peril = prl.Peril\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    ) acg\n" +
            "                                                                  ON    pa.ACCGRPID = acg.ACCGRPID\n" +
            "                                                      INNER JOIN (\n" +
            "                                                                        -------------------------------------------------------------\n" +
            "                                                                        -- Query to group an Cat Sepecific layer limits & deductible\n" +
            "                                                                        -------------------------------------------------------------\n" +
            "                                                                        SELECT    pa.PortInfoID, ac.ACCGRPID\n" +
            "                                                                              ,   ac.ACCGRPNUM, ac.ACCGRPNAME\n" +
            "                                                                              ,   SUBSTRING( Po.PolicyNum, 18, 2)        AS Peril\n" +
            "                                                                              ,   MAX( po.PARTOF )                       AS CatSubLimit\n" +
            "                                                                              ,   po.PartOfCur                           AS CatSubLimitCcy\n" +
            "                                                                              ,   MAX( CASE WHEN BlandedAmt > MaxDedAmt\n" +
            "                                                                                                  THEN CASE WHEN BlanDedAmt > MinDedAmt\n" +
            "                                                                                                                  THEN BlanDedAmt\n" +
            "                                                                                                                  ELSE MinDedAmt\n" +
            "                                                                                                         END\n" +
            "                                                                                                  ELSE CASE WHEN MaxDedAmt  > MinDedAmt\n" +
            "                                                                                                                  THEN MaxDedAmt\n" +
            "                                                                                                                  ELSE MinDedAmt\n" +
            "                                                                                                         END\n" +
            "                                                                                          END )\n" +
            "                                                                                                                         AS CatDeductible\n" +
            "                                                                              ,   po.BlandedCur                          AS CatDeductibleCcy\n" +
            "                                                                              ,   po.InceptDate                          AS InceptionDate\n" +
            "                                                                              ,   po.ExpireDate                          AS PracticalCompletionDate\n" +
            "                                                                        FROM  :edm:.dbo.portacct pa\n" +
            "                                                                              INNER JOIN :edm:.dbo.accgrp ac on ac.ACCGRPID = pa.ACCGRPID\n" +
            "                                                                              INNER JOIN :edm:.dbo.policy po on po.ACCGRPID = ac.ACCGRPID\n" +
            "                                                                        GROUP BY\n" +
            "                                                                                  pa.PortInfoID, ac.ACCGRPID\n" +
            "                                                                              ,   ac.ACCGRPNUM, ac.ACCGRPNAME\n" +
            "                                                                              ,   SUBSTRING( Po.PolicyNum, 18, 2)\n" +
            "                                                                              ,   po.PartOfCur\n" +
            "                                                                              ,   po.BlandedCur\n" +
            "                                                                              ,   po.InceptDate\n" +
            "                                                                              ,   po.ExpireDate\n" +
            "                                                                  ) pol\n" +
            "                                                            ON    pol.ACCGRPID  = acg.ACCGRPID\n" +
            "                                                            AND pol.Peril       = acg.Peril\n" +
            "                                          WHERE poi.PORTNUM = ?\n" +
            "                        ) t\n" +
            "            ) res\n" +
            "            LEFT  JOIN SCOR_REFERENCE.dbo.RegionPerilMapping rp1\n" +
            "                                    ON    rp1.SourceVendor = 'RMS'\n" +
            "                                    AND   rp1.PerilCode                 = res.PEril\n" +
            "                                    AND RTRIM(rp1.CountryCode)  = RTRIM(res.CountryCode)\n" +
            "                                    AND RTRIM(rp1.Admin1Code)   = RTRIM(res.LocationGroupCode)\n" +
            "            LEFT  JOIN scor_reference.dbo.RegionPerilMapping rp2\n" +
            "                        ON    rp2.SourceVendor = 'RMS'\n" +
            "                        AND   rp2.PerilCode         = IsNull(RTRIM(Convert(Varchar(10),res.Peril)),'')\n" +
            "                        AND IsNull(RTRIM(rp2.CountryCode),'')  = IsNull(RTRIM(res.CountryCode),'')\n" +
            "                        AND IsNull(RTRIM(rp2.Admin1Code),'')   = ''\n" +
            "GROUP BY\n" +
            "            res.CAR_ID\n" +
            "      ,     res.Division\n" +
            "      ,     Res.InceptionDate\n" +
            "      ,     res.PracticalCompletionDate\n" +
            "      ,     Coalesce( rp1.RegionPerilCode, rp2.RegionPerilCode )";

    public static final String REGION_PERIL_QUERY = "SELECT   an.ID              AS ANALYSIS_ID\n" +
            "    ,    av.ANALYSIS_COUNT  AS ANALYSIS_VERSION\n" +
            "    ,    an.NAME            AS PORTFOLIO_NUM\n" +
            "    ,    an.EXPOSUREID      AS PORTFOLIO_ID\n" +
            "    ,    an.DESCRIPTION     AS DLM_PROFILE_NAME\n" +
            "    ,    an.CURR            AS CURRENCY\n" +
            "    ,    an.RUNDATE         AS ANALYSIS_RUN_DATE\n" +
            "    ,    an.PERIL           AS PERIL\n" +
            "    ,    an.REGION          AS REGION\n" +
            "    ,    rap.Scope\n" +
            "    ,    rap.MutuallyExclusiveID\n" +
            "FROM     :rdm:.dbo.rdm_analysis an\n" +
            "         INNER JOIN SCOR_REFERENCE.dbo.RiskAnalysisProfileInfo rap\n" +
            "\t           ON  rap.ProfileName  \t= an.Description\n" +
            "\t   \t     AND rap.Scope   \t \t= 'Account'\n" +
            "\t   INNER JOIN  (\n" +
            "\t\t\t    SELECT\ta.NAME\n" +
            "\t\t\t\t  ,\ta.DESCRIPTION\n" +
            "\t\t\t\t  ,\tMAX(a.RUNDATE)\tAS LATEST_RUN_DATE\n" +
            "\t\t\t\t  ,\tCOUNT(1)\t\tAS ANALYSIS_COUNT\n" +
            "\t\t\t    FROM\t:rdm:.dbo.rdm_analysis a\n" +
            "\t\t\t    WHERE\ta.TYPE = 102 -- EP\n" +
            "\t\t\t    AND\ta.MODE = 2 -- Distributed\n" +
            "\t\t\t    AND\ta.STATUS = 102 -- Analyzed\n" +
            "\t\t\t    AND\ta.EXPOSURETYPE = 8017 -- Portfolio or group\n" +
            "\t\t\t    AND\ta.GROUPTYPE = 'ANLS' -- Analysis\n" +
            "\t\t\t    AND\ta.ENGINETYPE = 100 -- DLM\n" +
            "                      GROUP BY\n" +
            "                             a.NAME\n" +
            "                         ,   a.DESCRIPTION\n" +
            "\t              ) av\n" +
            "\t\t    ON  av.NAME = an.NAME\n" +
            "\t\t    AND av.LATEST_RUN_DATE = an.RUNDATE\n" +
            "\t\t    AND av.DESCRIPTION = an.DESCRIPTION\n" +
            "WHERE\t  an.ID = ? -- Analysis Id.\n" +
            "AND\t  an.TYPE = 102 -- EP\n" +
            "AND\t  an.MODE = 2 -- Distributed\n" +
            "AND\t  an.STATUS = 102 -- Analyzed\n" +
            "AND\t  an.EXPOSURETYPE = 8017 -- Portfolio or group\n" +
            "AND\t  an.GROUPTYPE = 'ANLS' -- Analysis\n" +
            "AND\t  an.ENGINETYPE = 100 -- DLM\n" +
            "AND\t  EXISTS (\n" +
            "\t     \t    SELECT\t1\n" +
            "\t\t    FROM\t:rdm:.dbo.rdm_portstats ps\n" +
            "\t\t    WHERE\tps.ANLSID = an.ID\n" +
            "\t\t    AND     ps.PERSPCODE = 'GU'\n" +
            "\t         )\n" +
            "ORDER BY an.ID";

    public static final String RL_LOC_QUERY = "SELECT\t  DISTINCT\n" +
            "\t\t        res.*\n" +
            "                ,   Coalesce( rp1.RegionPerilCode, rp2.RegionPerilCode ) AS RegionPerilCode\n" +
            "            FROM  (\n" +
            "\t\t\t    SELECT  acc.USERID4\t\t\t\t\t\t\t\t\tAS CAR_ID\n" +
            "\t\t\t\t,   Substring( acc.ACCGRPNUM, 10, 2)\t\t\tAS DivisionID\n" +
            "\t\t\t\t,\tCASE WHEN  adr.CountryRMSCode = 'CB'\n" +
            "\t\t\t\t\t\t THEN  adr.CountryCode\n" +
            "\t\t\t\t\t\t ELSE  adr.CountryRMSCode\n" +
            "                \tEND as CountryCode\n" +
            "\t\t\t\t,   adr.Admin1Code\t\t\t\t\t   \t\t\tAS RegionCode\n" +
            "\t\t\t\t,   lcv.Peril                            \t\tAS PerilCode\n" +
            "\t\t\t\t,   ppt.LOCID                            \t\tAS LocationID\n" +
            "\t\t\t\t,   ppt.LOCNUM                           \t\tAS LocationNum\n" +
            "\t\t\t\t,   ppt.LOCNAME                          \t\tAS LocationName\n" +
            "\t\t\t\t,\tadr.StreetAddress\t\t\t\t\t\t\tAS StreetAddress\n" +
            "\t\t\t\t,\tadr.CityName\t\t\t\t\t\t\t\tAS CityName\n" +
            "\t\t\t\t,\tadr.Admin2Name\t\t\t\t\t\t\t\tAS County\n" +
            "\t\t\t\t,\tadr.Admin1Name\t\t\t\t\t\t\t\tAS State\n" +
            "\t\t\t\t,\tadr.PostalCode\t\t\t\t\t\t\t\tAS PostalCode\n" +
            "\t\t\t\t,   ppt.BLDGSCHEME                       \t\tAS BldgScheme\n" +
            "\t\t\t\t,   ppt.BLDGCLASS                        \t\tAS BldgClass\n" +
            "\t\t\t\t,   Coalesce(vulnCons.NAME, '** Invalid Mapping **' )\tAs ConsName\n" +
            "\t\t\t\t,   ppt.OCCSCHEME                        \t\tAs OccScheme\n" +
            "\t\t\t\t,   ppt.OCCTYPE                          \t\tAs OccType\n" +
            "\t\t\t\t,   Coalesce(vulnOcc.NAME, '** Invalid Mapping **' )\tAs OccName\n" +
            "\t\t\t\t,   pir.CATEGORY                         \t\tAS WIND_ZONE\n" +
            "\t\t\t\t,   geo.ISO3A\t\t\t\t\t\t\t\t\tAS ISO3A\n" +
            "\t\t\t\t,   adr.CountryRMSCode\t\t\t\t\t\t\tAS CountryRMSCode\n" +
            "\t\t\t\t,   adr.Admin1Code\t\t\t\t\t\t\t\tAS Admin1Code\n" +
            "\t\t\t\t,   lcv.TIV_BUILDINGS +\n" +
            "\t\t\t\t\tlcv.TIV_CONTENTS  +\n" +
            "\t\t\t\t\tlcv.TIV_BI\t\t\t\t\t\t\t\t\tAS TIV_Value\n" +
            "\t\t\t\t,   Coalesce(rls.PURE_PREMIUM_GU,0)\t\t\t\tAS PurePremium_GU\n" +
            "\t\t\t\t,   Coalesce(rls.PURE_PREMIUM_GR,0)\t\t\t\tAS PurePremium_GR\n" +
            "\t\t\t\t,   lcv.TIV_BUILDINGS\t\t\t\t\t\t\tAS TIV_Buildings\n" +
            "\t\t\t\t,   lcv.TIV_CONTENTS\t\t\t\t\t\t\tAS TIV_Contents\n" +
            "\t\t\t\t,   lcv.TIV_BI\t\t\t\t\t\t\t\t\tAS TIV_BI\n" +
            "\t\t\t\t,   lcv.TIV_BUILDINGS +\n" +
            "\t\t\t\t\tlcv.TIV_CONTENTS  +\n" +
            "\t\t\t\t\tlcv.TIV_BI\t\t\t\t\t\t\t\t\tAS TIV_Combined\n" +
            "\t\t\t\t,   adr.GeoResolutionCode\t\t\t\t\t\tAS GeoResultionCode\n" +
            "\t\t\t\t,\tCoalesce(fld.FLZone,     'Unknown' )\t\tAS PrimaryFloodZone\n" +
            "\t\t\t\t,\tCoalesce(fld.OtherZones, 'Unknown' )\t\tAS NeighboringFloodZones\n" +
            "\t\t\t\t,\tCoalesce(fld.AnnProb,    'Unknown' )\t\tAS AnnualProbabilityofFlooding\n" +
            "\t\t\t\t,\thud.DISTCOAST\t\t\t\t\t\t\t\tAS DistCoast\n" +
            "\t\t\t\t,\thud.ELEVATION\t\t\t\t\t\t\t\tAS Elevation\n" +
            "\t\t\t\t,\teqd.SOILTYPE\t\t\t\t\t\t\t\tAS SoilType\n" +
            "\t\t\t\t,\tCASE eqd.SOILTYPE\n" +
            "\t\t\t\t\t\tWHEN 0 THEN 'Unknown'\n" +
            "\t\t\t\t\t\tELSE lk_soil.soil_type_name\n" +
            "\t\t\t\t\tEND\t\t\t\t\t\t\t\t\t\t\tAS SoilTypeName\n" +
            "\t\t\t\t,\teqd.SOILMATCH\t\t\t\t\t\t\t\tAS SoilMatch\n" +
            "\t\t\t\t,\tsoil_match.VALUENAME\t\t\t\t\t\tAS SoilMatchLevel\n" +
            "\t\t\t\t,\teqd.LIQUEFACT\t\t\t\t\t\t\t\tAS Liquefact\n" +
            "\t\t\t\t,\tCASE eqd.LIQUEFACT\n" +
            "\t\t\t\t\t\tWHEN 0 THEN 'Unknown'\n" +
            "\t\t\t\t\t\tELSE lk_liquefact.liquefaction_name\n" +
            "\t\t\t\t\tEND\t\t\t\t\t\t\t\t\t\t\tAS LiquefactionName\n" +
            "\t\t\t\t,\teqd.LIQUEMATCH\t\t\t\t\t\t\t\tAS LiqueMatch\n" +
            "\t\t\t\t,\tlique_match.VALUENAME\t\t\t\t\t\tAS LiquefactionMatchLevel\n" +
            "\t\t\t\t,\teqd.LANDSLIDE\t\t\t\t\t\t\t\tAS Landslide\n" +
            "\t\t\t\t,\tCASE eqd.LANDSLIDE\n" +
            "\t\t\t\t\t\tWHEN 0 THEN 'Unknown'\n" +
            "\t\t\t\t\t\tELSE lk_land.landslide_name\n" +
            "\t\t\t\t\tEND\t\t\t\t\t\t\t\t\t\t\tAS LandslideName\n" +
            "\t\t\t\t,\teqd.LANDMATCH\t\t\t\t\t\t\t\tAS LandMatch\n" +
            "\t\t\t\t,\tland_match.VALUENAME\t\t\t\t\t\tAS LandslideMatchLevel\n" +
            "            FROM\t:edm:.dbo.portinfo poi\n" +
            "                    INNER JOIN :edm:.dbo.portacct pra\n" +
            "                               ON  poi.PORTINFOID = pra.PORTINFOID\n" +
            "                    INNER JOIN :edm:.dbo.accgrp acc\n" +
            "                               ON  pra.ACCGRPID = acc.ACCGRPID\n" +
            "                    INNER JOIN :edm:.dbo.policy plc\n" +
            "                               ON  acc.ACCGRPID = plc.ACCGRPID\n" +
            "                    INNER JOIN :edm:.dbo.Property ppt\n" +
            "                               ON  acc.ACCGRPID = ppt.ACCGRPID\n" +
            "                    INNER JOIN :edm:.dbo.Address adr\n" +
            "                               ON  ppt.AddressID = adr.AddressID\n" +
            "                    LEFT JOIN  (\n" +
            "\t\t\t\t\t\t\t\tSELECT\tlcv.LOCID\n" +
            "\t\t\t\t\t\t\t\t\t,\tlcv.PERIL\n" +
            "                                    ,   SUM( CASE WHEN LOSSTYPE = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  THEN lcv.VALUEAMT * (LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "                                                  ELSE 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\t  END )            AS TIV_BUILDINGS\n" +
            "\t\t\t\t\t\t\t\t\t,\tSUM( CASE WHEN LOSSTYPE = 2\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  THEN lcv.VALUEAMT * (LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  ELSE 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\t END )             AS TIV_CONTENTS\n" +
            "\t\t\t\t\t\t\t\t\t,\tSUM( CASE WHEN LOSSTYPE = 3\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  THEN lcv.VALUEAMT * (LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  ELSE 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\t END )             AS TIV_BI\n" +
            "\t\t\t\t\t\t\t\t\t,\t(LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   AS FX_CROSSRATE\n" +
            "\t\t\t\t\t\t\t\tFROM\t:edm:.dbo.loccvg lcv\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN rms_userconfig.dbo.currfx LocFX\n" +
            "                                                ON  LocFX.CODE  = lcv.VALUECUR\n" +
            "                                                AND LocFX.XDATE = (\n" +
            "                                                                   SELECT  MAX( XDATE )\n" +
            "                                                                   FROM    rms_userconfig.dbo.currfx LocFX\n" +
            "                                                                   WHERE   CODE = 'USD'\n" +
            "                                                                  )\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN rms_userconfig.dbo.currfx LimFX\n" +
            "                                 \t\t\t\tON  LimFX.CODE  =  ?\n" +
            "                                                AND LimFX.XDATE = (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSELECT  MAX( XDATE )\n" +
            "                                                                    FROM    rms_userconfig.dbo.currfx LocFX\n" +
            "                                                                    WHERE   CODE = 'USD'\n" +
            "                                                                  )\n" +
            "\t\t\t\t\t\t\t\tWHERE lcv.LossType <> 4 -- ** ADDED FOR REMOVE REDUNDANT DATA**\n" +
            "\t\t\t\t\t\t\t\tAND\t  lcv.Peril < 5\n" +
            "                                GROUP BY\n" +
            "\t\t\t\t\t\t\t\t\t\tlcv.LOCID\n" +
            "\t\t\t\t\t\t\t\t   ,\tlcv.PERIL\n" +
            "\t\t\t\t\t\t\t\t   ,\t(LocFX.XFACTOR * (1/LimFX.XFactor))\n" +
            "\t\t\t\t\t\t\t\t) lcv\n" +
            "\t\t\t\t\t\t\tON  lcv.LOCID   = ppt.LOCID\n" +
            "                            AND lcv.PERIL   = plc.POLICYTYPE\n" +
            "            \t\tINNER JOIN :edm:.dbo.loc loc\n" +
            "\t\t\t\t\t\t\tON  loc.LOCID = lcv.LOCID\n" +
            "                    LEFT  JOIN RMS_SYSTEMDATA.dbo.vulnnames vulnOcc\n" +
            "                            ON  vulnOcc.COUNTRY     = adr.CountryRMSCode\n" +
            "                            AND vulnOcc.SCHEME      = ppt.OCCSCHEME\n" +
            "                            AND vulnOcc.CODE        = ppt.OCCTYPE\n" +
            "                            AND vulnOcc.attribid    = 521\n" +
            "\t\t\t\t\tLEFT  JOIN RMS_SYSTEMDATA.dbo.vulnnames vulnCons\n" +
            "\t\t\t\t\t\t\tON  vulnCons.COUNTRY    = adr.CountryRMSCode\n" +
            "                            AND vulnCons.SCHEME     = ppt.BLDGSCHEME\n" +
            "                            AND vulnCons.CODE       = ppt.BLDGCLASS\n" +
            "                            AND vulnCons.attribid   = 501\n" +
            "\t\t\t\t\tLEFT  JOIN (\n" +
            "\t\t\t\t\t\t\t\tSELECT\trls.ID AS LocID\n" +
            "\t\t\t\t\t\t\t\t\t,\tSUM( CASE WHEN rls.PERSPCODE = 'GU'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  THEN PUREPREMIUM\n" +
            "                                                  ELSE 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\t END)             AS PURE_PREMIUM_GU\n" +
            "\t\t\t\t\t\t\t\t\t,   SUM( CASE WHEN rls.PERSPCODE = 'GR'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t  THEN PUREPREMIUM\n" +
            "                                                  ELSE 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\t END )\t\t\t  AS PURE_PREMIUM_GR\n" +
            "\t\t\t\t\t\t\t\tFROM    :rdm:.dbo.rdm_locstats rls\n" +
            "\t\t\t\t\t\t\t\t\t\tINNER JOIN (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tSELECT  t.ID, t.Name\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t,\tt.Description\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t,   t.PORTINFOID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t,   t.SeqOrder\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tFROM    (\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t-- Identify the latest analysis for a\n" +
            "                                                            -- given dlm profile and exposureport ID\n" +
            "                                                            SELECT  ra.ID, ra.Name\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t,  ra.Description\n" +
            "                                                                ,  pio.PortInfoID\n" +
            "                                                                ,  ra.Status\n" +
            "                                                                ,  ROW_NUMBER() OVER\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t(\n" +
            "                                                                                    PARTITION BY\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tra.Description\n" +
            "                                                                                              , ra.ExposureID\n" +
            "                                                                                        ORDER BY\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tra.RunDate desc\n" +
            "                                                                                    ) AS SeqOrder\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tFROM    :rdm:.dbo.rdm_analysis ra\n" +
            "                                                \t\t\t\tINNER JOIN :edm:.dbo.PortInfo pio\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tON  pio.PortInfoID = ra.ExposureID\n" +
            "                                                                            AND pio.PortNum    = ra.Name\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE   pio.PortNum =   ?\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t) t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\tWHERE\tt.SeqOrder = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t) rda\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tON  rls.ANLSID = rda.ID\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\tAND rls.PERSPCODE IN ('GU','GR')\n" +
            "                                                AND EPTYPE     = 1\n" +
            "\t\t\t\t\t\t\t\tGROUP BY  rls.ID\n" +
            "                                ) rls\n" +
            "\t\t\t\t\t\t\tON  rls.LocID  = ppt.LOCID\n" +
            "\t\t\t\t\tINNER JOIN rms_geography.dbo.country geo\n" +
            "\t\t\t\t\t\t\tON  adr.CountryCode     = geo.ISO2A\n" +
            "\t\t\t\t\tLEFT  JOIN rms_systemdata.dbo.pisrperildetail pir\n" +
            "\t\t\t\t\t\t\tON  adr.Admin1Code      = pir.DEPENDANCY1\n" +
            "                           AND\tadr.CountryRMSCode  = pir.DEPENDANCY2\n" +
            "                           AND\tadr.Admin2Code      = pir.VALUE\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.locconditions lcc\n" +
            "\t\t\t\t\t\t\tON  ppt.LOCID = lcc.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.policyconditions pcc\n" +
            "\t\t\t\t\t\t\tON  plc.POLICYID        = pcc.POLICYID\n" +
            "                            AND lcc.CONDITIONID     = pcc.CONDITIONID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.eqdet eqd ON  ppt.LOCID = eqd.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.hudet hud ON  ppt.LOCID = hud.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.todet tod ON  ppt.LOCID = tod.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.fldet fld ON  ppt.LOCID = fld.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.frdet frd ON  ppt.LOCID = frd.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.dbo.trdet trd ON  ppt.LOCID = trd.LOCID\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.Lookup.SoilType lk_soil\n" +
            "\t\t\t\t\t\tON (eqd.SOILTYPE = lk_soil.band_min AND eqd.SOILTYPE = lk_soil.band_max)\n" +
            "\t\t\t\t\t\tOR (eqd.SOILTYPE > lk_soil.band_min AND eqd.SOILTYPE <= lk_soil.band_max)\n" +
            "\t\t\t\t\tLEFT  JOIN RMS_SYSTEMDATA.dbo.fldval soil_match\n" +
            "\t\t\t\t\t\tON soil_match.COUNTRY = adr.CountryRMSCode\n" +
            "\t\t\t\t\t\tAND soil_match.FLDID = 351\n" +
            "\t\t\t\t\t\tAND soil_match.VALUECODE = eqd.SOILMATCH\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.Lookup.Liquefaction lk_liquefact\n" +
            "\t\t\t\t\t\tON (eqd.LIQUEFACT = lk_liquefact.band_min AND eqd.LIQUEFACT = lk_liquefact.band_max)\n" +
            "\t\t\t\t\t\tOR (eqd.LIQUEFACT > lk_liquefact.band_min AND eqd.LIQUEFACT <= lk_liquefact.band_max)\n" +
            "\t\t\t\t\tLEFT  JOIN RMS_SYSTEMDATA.dbo.fldval lique_match\n" +
            "\t\t\t\t\t\tON lique_match.COUNTRY = adr.CountryRMSCode\n" +
            "\t\t\t\t\t\tAND lique_match.FLDID = 357\n" +
            "\t\t\t\t\t\tAND lique_match.VALUECODE = eqd.LIQUEMATCH\n" +
            "\t\t\t\t\tLEFT  JOIN :edm:.Lookup.LandSlide lk_land\n" +
            "\t\t\t\t\t\tON (eqd.LANDSLIDE = lk_land.band_min AND eqd.LANDSLIDE = lk_land.band_max)\n" +
            "\t\t\t\t\t\tOR (eqd.LANDSLIDE > lk_land.band_min AND eqd.LANDSLIDE <= lk_land.band_max)\n" +
            "\t\t\t\t\tLEFT  JOIN RMS_SYSTEMDATA.dbo.fldval land_match\n" +
            "\t\t\t\t\t\tON land_match.COUNTRY = adr.CountryRMSCode\n" +
            "\t\t\t\t\t\tAND land_match.FLDID = 354\n" +
            "\t\t\t\t\t\tAND land_match.VALUECODE = eqd.LANDMATCH\n" +
            "\t\t\tWHERE   poi.PortNum =  ?\n" +
            "            ) res\n" +
            "\tLEFT  JOIN SCOR_REFERENCE.dbo.RegionPerilMapping rp1\n" +
            "\t\t\tON  rp1.SourceVendor        = 'RMS'\n" +
            "            AND rp1.PerilCode           = RTRIM(Convert(Varchar(10),res.PerilCode))\n" +
            "            AND RTRIM(rp1.CountryCode)  = RTRIM(res.CountryCode)\n" +
            "            AND RTRIM(rp1.Admin1Code)   = RTRIM(res.Admin1Code)\n" +
            "\tLEFT  JOIN scor_reference.dbo.RegionPerilMapping rp2\n" +
            "\t\t\tON  rp2.SourceVendor        = 'RMS'\n" +
            "            AND rp2.PerilCode           = RTRIM(Convert(Varchar(10),res.PerilCode))\n" +
            "            AND RTRIM(rp2.CountryCode)  = RTRIM(res.CountryCode)\n" +
            "            AND RTRIM(rp2.Admin1Code)   = ''\n" +
            "OPTION (FORCE ORDER)  -- ** ADDED FOR PERFORMANCE **";
    public static final String RL_ACC_QUERY_PROC = "Exec @database.dbo.RR_RL_GetEdmExtractACCFile @edm_name= ?, @portnum= ?";
    public static final String RL_LOC_QUERY_PROC = "Exec @database.dbo.RR_RL_GetEdmExtractLOCFile @edm_name= ?, @rdm_name= ?, @portnum= ?, @ccy_code= ?";
}
