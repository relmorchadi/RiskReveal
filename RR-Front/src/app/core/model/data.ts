const countries = [
  {shortName: 'ARUBA', countryCode: 'ABW'},
  {shortName: 'AFGHANISTAN', countryCode: 'AFG'},
  {shortName: 'ANGOLA', countryCode: 'AGO'},
  {shortName: 'ANGUILLA', countryCode: 'AIA'},
  {shortName: 'ALBANIA', countryCode: 'ALB'},
  {shortName: 'ANDORRA', countryCode: 'AND'},
  {shortName: 'NETHERLANDS ANTI', countryCode: 'ANT'},
  {shortName: 'UNITED ARAB EMIR', countryCode: 'ARE'},
  {shortName: 'ARGENTINA', countryCode: 'ARG'},
  {shortName: 'ARMENIA', countryCode: 'ARM'},
  {shortName: 'SAMOA (AMERICAN)', countryCode: 'ASM'},
  {shortName: 'ANTIGUA&BARBUDA', countryCode: 'ATG'},
  {shortName: 'AUSTRALIA', countryCode: 'AUS'},
  {shortName: 'AUSTRIA', countryCode: 'AUT'},
  {shortName: 'AZERBAIJAN', countryCode: 'AZE'},
  {shortName: 'BURUNDI', countryCode: 'BDI'},
  {shortName: 'BELGIUM', countryCode: 'BEL'},
  {shortName: 'BENIN', countryCode: 'BEN'},
  {shortName: 'BURKINA FASO', countryCode: 'BFA'},
  {shortName: 'BANGLADESH', countryCode: 'BGD'},
  {shortName: 'BULGARIA', countryCode: 'BGR'},
  {shortName: 'BAHRAIN', countryCode: 'BHR'},
  {shortName: 'BAHAMAS', countryCode: 'BHS'},
  {shortName: 'BOSNIA-HERZEGOV.', countryCode: 'BIH'},
  {shortName: 'BELARUS', countryCode: 'BLR'},
  {shortName: 'BELIZE', countryCode: 'BLZ'},
  {shortName: 'BERMUDA', countryCode: 'BMU'},
  {shortName: 'BOLIVIA', countryCode: 'BOL'},
  {shortName: 'BRAZIL', countryCode: 'BRA'},
  {shortName: 'BARBADOS', countryCode: 'BRB'},
  {shortName: 'BRUNEI', countryCode: 'BRN'},
  {shortName: 'BHUTAN', countryCode: 'BTN'},
  {shortName: 'BOTSWANA', countryCode: 'BWA'},
  {shortName: 'CENTRAL AFRICAN', countryCode: 'CAF'},
  {shortName: 'CANADA', countryCode: 'CAN'},
  {shortName: 'SWITZERLAND', countryCode: 'CHE'},
  {shortName: 'CHILE', countryCode: 'CHL'},
  {shortName: 'CHINA', countryCode: 'CHN'},
  {shortName: 'IVORY COAST', countryCode: 'CIV'},
  {shortName: 'CAMEROON', countryCode: 'CMR'},
  {shortName: 'RD CONGO', countryCode: 'COD'},
  {shortName: 'CONGO', countryCode: 'COG'},
  {shortName: 'COOK ISLANDS', countryCode: 'COK'},
  {shortName: 'COLOMBIA', countryCode: 'COL'},
  {shortName: 'COMOROS', countryCode: 'COM'},
  {shortName: 'CAPE VERDE', countryCode: 'CPV'},
  {shortName: 'COSTA RICA', countryCode: 'CRI'},
  {shortName: 'CUBA', countryCode: 'CUB'},
  {shortName: 'CURACAO', countryCode: 'CUW'},
  {shortName: 'CAYMAN ISLANDS', countryCode: 'CYM'},
  {shortName: 'CYPRUS', countryCode: 'CYP'},
  {shortName: 'CZECH REPUBLIC', countryCode: 'CZE'},
  {shortName: 'GERMANY', countryCode: 'DEU'},
  {shortName: 'DJIBOUTI', countryCode: 'DJI'},
  {shortName: 'DOMINICA', countryCode: 'DMA'},
  {shortName: 'DENMARK', countryCode: 'DNK'},
  {shortName: 'DOMINICAN REP.', countryCode: 'DOM'},
  {shortName: 'ALGERIA', countryCode: 'DZA'},
  {shortName: 'ECUADOR', countryCode: 'ECU'},
  {shortName: 'EGYPT', countryCode: 'EGY'},
  {shortName: 'ERITREA', countryCode: 'ERI'},
  {shortName: 'SPAIN', countryCode: 'ESP'},
  {shortName: 'ESTONIA', countryCode: 'EST'},
  {shortName: 'ETHIOPIA', countryCode: 'ETH'},
  {shortName: 'FINLAND', countryCode: 'FIN'},
  {shortName: 'FIJI', countryCode: 'FJI'},
  {shortName: 'FRANCE', countryCode: 'FRA'},
  {shortName: 'FAROE ISLANDS', countryCode: 'FRO'},
  {shortName: 'MICRONESIA', countryCode: 'FSM'},
  {shortName: 'GABON', countryCode: 'GAB'},
  {shortName: 'UNITED KINGDOM', countryCode: 'GBR'},
  {shortName: 'GEORGIA', countryCode: 'GEO'},
  {shortName: 'GUERNSEY', countryCode: 'GGY'},
  {shortName: 'GHANA', countryCode: 'GHA'},
  {shortName: 'GIBRALTAR', countryCode: 'GIB'},
  {shortName: 'GUINEA', countryCode: 'GIN'},
  {shortName: 'GAMBIA', countryCode: 'GMB'},
  {shortName: 'GUINEA-BISSAU', countryCode: 'GNB'},
  {shortName: 'EQUATOR. GUINEA', countryCode: 'GNQ'},
  {shortName: 'GREECE', countryCode: 'GRC'},
  {shortName: 'GRENADA', countryCode: 'GRD'},
  {shortName: 'GROENLAND', countryCode: 'GRL'},
  {shortName: 'GUATEMALA', countryCode: 'GTM'},
  {shortName: 'GUAM', countryCode: 'GUM'},
  {shortName: 'GUYANA', countryCode: 'GUY'},
  {shortName: 'HONG KONG', countryCode: 'HKG'},
  {shortName: 'HONDURAS', countryCode: 'HND'},
  {shortName: 'CROATIA', countryCode: 'HRV'},
  {shortName: 'HAITI', countryCode: 'HTI'},
  {shortName: 'HUNGARY', countryCode: 'HUN'},
  {shortName: 'INDONESIA', countryCode: 'IDN'},
  {shortName: 'ISLE OF MAN', countryCode: 'IMN'},
  {shortName: 'INDIA', countryCode: 'IND'},
  {shortName: 'BRITISH IOT', countryCode: 'IOT'},
  {shortName: 'IRELAND', countryCode: 'IRL'},
  {shortName: 'IRAN', countryCode: 'IRN'},
  {shortName: 'IRAQ', countryCode: 'IRQ'},
  {shortName: 'ICELAND', countryCode: 'ISL'},
  {shortName: 'ISRAEL', countryCode: 'ISR'},
  {shortName: 'ITALY', countryCode: 'ITA'},
  {shortName: 'JAMAICA', countryCode: 'JAM'},
  {shortName: 'JERSEY', countryCode: 'JEY'},
  {shortName: 'JORDAN', countryCode: 'JOR'},
  {shortName: 'JAPAN', countryCode: 'JPN'},
  {shortName: 'KAZAKHSTAN', countryCode: 'KAZ'},
  {shortName: 'KENYA', countryCode: 'KEN'},
  {shortName: 'KYRGYZSTAN', countryCode: 'KGZ'},
  {shortName: 'CAMBODIA', countryCode: 'KHM'},
  {shortName: 'KIRIBATI', countryCode: 'KIR'},
  {shortName: 'SAINT KITTS ...', countryCode: 'KNA'},
  {shortName: 'KOREA SOUTH', countryCode: 'KOR'},
  {shortName: 'KUWAIT', countryCode: 'KWT'},
  {shortName: 'LAO', countryCode: 'LAO'},
  {shortName: 'LEBANON', countryCode: 'LBN'},
  {shortName: 'LIBERIA', countryCode: 'LBR'},
  {shortName: 'LIBYA', countryCode: 'LBY'},
  {shortName: 'SAINT LUCIA', countryCode: 'LCA'},
  {shortName: 'LIECHTENSTEIN', countryCode: 'LIE'},
  {shortName: 'SRI LANKA', countryCode: 'LKA'},
  {shortName: 'LESOTHO', countryCode: 'LSO'},
  {shortName: 'LITHUANIA', countryCode: 'LTU'},
  {shortName: 'LUXEMBOURG', countryCode: 'LUX'},
  {shortName: 'LATVIA', countryCode: 'LVA'},
  {shortName: 'MACAU', countryCode: 'MAC'},
  {shortName: 'MOROCCO', countryCode: 'MAR'},
  {shortName: 'MONACO', countryCode: 'MCO'},
  {shortName: 'MOLDOVA', countryCode: 'MDA'},
  {shortName: 'MADAGASCAR', countryCode: 'MDG'},
  {shortName: 'MALDIVES', countryCode: 'MDV'},
  {shortName: 'MEXICO', countryCode: 'MEX'},
  {shortName: 'MARSHALL ISLANDS', countryCode: 'MHL'},
  {shortName: 'MACEDONIA', countryCode: 'MKD'},
  {shortName: 'MALI', countryCode: 'MLI'},
  {shortName: 'MALTA', countryCode: 'MLT'},
  {shortName: 'BURMA', countryCode: 'MMR'},
  {shortName: 'MONTENEGRO', countryCode: 'MNE'},
  {shortName: 'MONGOLIA', countryCode: 'MNG'},
  {shortName: 'MARIANA', countryCode: 'MNP'},
  {shortName: 'MOZAMBIQUE', countryCode: 'MOZ'},
  {shortName: 'MAURITANIA', countryCode: 'MRT'},
  {shortName: 'MONTSERRAT', countryCode: 'MSR'},
  {shortName: 'MAURITIUS', countryCode: 'MUS'},
  {shortName: 'MALAWI', countryCode: 'MWI'},
  {shortName: 'MALAYSIA', countryCode: 'MYS'},
  {shortName: 'NAMIBIA', countryCode: 'NAM'},
  {shortName: 'NIGER', countryCode: 'NER'},
  {shortName: 'NIGERIA', countryCode: 'NGA'},
  {shortName: 'NICARAGUA', countryCode: 'NIC'},
  {shortName: 'NETHERLANDS', countryCode: 'NLD'},
  {shortName: 'NORWAY', countryCode: 'NOR'},
  {shortName: 'NEPAL', countryCode: 'NPL'},
  {shortName: 'NAURU', countryCode: 'NRU'},
  {shortName: 'NEW ZEALAND', countryCode: 'NZL'},
  {shortName: 'OMAN', countryCode: 'OMN'},
  {shortName: 'PAKISTAN', countryCode: 'PAK'},
  {shortName: 'PALESTINE', countryCode: 'PAL'},
  {shortName: 'PANAMA', countryCode: 'PAN'},
  {shortName: 'PITCAIRN', countryCode: 'PCN'},
  {shortName: 'PERU', countryCode: 'PER'},
  {shortName: 'PHILIPPINES', countryCode: 'PHL'},
  {shortName: 'PALAU Republic', countryCode: 'PLW'},
  {shortName: 'PAPUA NEW GUINEA', countryCode: 'PNG'},
  {shortName: 'POLAND', countryCode: 'POL'},
  {shortName: 'PUERTO RICO', countryCode: 'PRI'},
  {shortName: 'KOREA NORTH', countryCode: 'PRK'},
  {shortName: 'PORTUGAL', countryCode: 'PRT'},
  {shortName: 'PARAGUAY', countryCode: 'PRY'},
  {shortName: 'QATAR', countryCode: 'QAT'},
  {shortName: 'ROMANIA', countryCode: 'ROM'},
  {shortName: 'RUSSIA', countryCode: 'RUS'},
  {shortName: 'RWANDA', countryCode: 'RWA'},
  {shortName: 'SAUDI ARABIA', countryCode: 'SAU'},
  {shortName: 'YUGOSLAVIA', countryCode: 'SCG'},
  {shortName: 'SUDAN', countryCode: 'SDN'},
  {shortName: 'SENEGAL', countryCode: 'SEN'},
  {shortName: 'SINGAPORE', countryCode: 'SGP'},
  {shortName: 'SOUTH GEORGIA...', countryCode: 'SGS'},
  {shortName: 'SOLOMON ISLANDS', countryCode: 'SLB'},
  {shortName: 'SIERRA LEONE', countryCode: 'SLE'},
  {shortName: 'EL SALVADOR', countryCode: 'SLV'},
  {shortName: 'SAN MARINO', countryCode: 'SMR'},
  {shortName: 'SOMALIA', countryCode: 'SOM'},
  {shortName: 'SERBIA', countryCode: 'SRB'},
  {shortName: 'SOUTH SUDAN', countryCode: 'SSD'},
  {shortName: 'SAO TOME', countryCode: 'STP'},
  {shortName: 'SURINAME', countryCode: 'SUR'},
  {shortName: 'SLOVAKIA', countryCode: 'SVK'},
  {shortName: 'SLOVENIA', countryCode: 'SVN'},
  {shortName: 'SWEDEN', countryCode: 'SWE'},
  {shortName: 'SWAZILAND', countryCode: 'SWZ'},
  {shortName: 'SINT MAARTEN', countryCode: 'SXM'},
  {shortName: 'SEYCHELLES', countryCode: 'SYC'},
  {shortName: 'SYRIA', countryCode: 'SYR'},
  {shortName: 'TURKS CAICOS', countryCode: 'TCA'},
  {shortName: 'CHAD', countryCode: 'TCD'},
  {shortName: 'TOGO', countryCode: 'TGO'},
  {shortName: 'THAILAND', countryCode: 'THA'},
  {shortName: 'TAJIKISTAN', countryCode: 'TJK'},
  {shortName: 'TURKMENISTAN', countryCode: 'TKM'},
  {shortName: 'TONGA', countryCode: 'TON'},
  {shortName: 'TRINIDAD&TOBAGO', countryCode: 'TTO'},
  {shortName: 'TUNISIA', countryCode: 'TUN'},
  {shortName: 'TURKEY', countryCode: 'TUR'},
  {shortName: 'TAIWAN', countryCode: 'TWN'},
  {shortName: 'TANZANIA', countryCode: 'TZA'},
  {shortName: 'UGANDA', countryCode: 'UGA'},
  {shortName: 'UKRAINE', countryCode: 'UKR'},
  {shortName: 'URUGUAY', countryCode: 'URY'},
  {shortName: 'USA', countryCode: 'USA'},
  {shortName: 'U.S. Midwest', countryCode: 'USU'},
  {shortName: 'U.S. Northeast', countryCode: 'USV'},
  {shortName: 'U.S. West', countryCode: 'USW'},
  {shortName: 'U S. Mid-atlanti', countryCode: 'USX'},
  {shortName: 'U.S. Southeast', countryCode: 'USY'},
  {shortName: 'U.S. South Centr', countryCode: 'USZ'},
  {shortName: 'UZBEKISTAN', countryCode: 'UZB'},
  {shortName: 'VATICAN', countryCode: 'VAT'},
  {shortName: 'SAINT VINCENT...', countryCode: 'VCT'},
  {shortName: 'VENEZUELA', countryCode: 'VEN'},
  {shortName: 'VIRGIN (GBR)', countryCode: 'VGB'},
  {shortName: 'VIRGIN (USA)', countryCode: 'VIR'},
  {shortName: 'VIET NAM', countryCode: 'VNM'},
  {shortName: 'VANUATU', countryCode: 'VUT'},
  {shortName: 'SAMOA', countryCode: 'WSM'},
  {shortName: 'YEMEN', countryCode: 'YEM'},
  {shortName: 'YUGOSLAVIA Old', countryCode: 'YUG'},
  {shortName: 'SOUTH AFRICA', countryCode: 'ZAF'},
  {shortName: 'ZAIRE Old', countryCode: 'ZAR'},
  {shortName: 'ZAMBIA', countryCode: 'ZMB'},
  {shortName: 'ZIMBABWE', countryCode: 'ZWE'}
];

const uwUnit = [
  {id: 170206, name: 'Agro', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 24103, name: 'Argentina', subsidiaryId: [2], subsidiaryLedger: 7, marketType: 'TRT'},
  {id: 20701, name: 'Argentina', subsidiaryId: [2], subsidiaryLedger: 7, marketType: 'TRT'},
  {id: 170202, name: 'Austria Czech Slov.', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 174811, name: 'Aviation', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 200701, name: 'Beijing', subsidiaryId: [20], subsidiaryLedger: 7, marketType: 'TRT'},
  {id: 200204, name: 'Beijing Gen Man', subsidiaryId: [20], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 20102, name: 'Belgium/Lux.', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 101203, name: 'Bogota', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'TRT'},
  {id: 104084, name: 'Brazil Underwriting treaties', subsidiaryId: [10], subsidiaryLedger: 13, marketType: 'TRT'},
  {id: 101205, name: 'Buenos-Aires', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'TRT'},
  {id: 104085, name: 'Carribea, Mexico and CA Treaty', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'TRT'},
  {id: 174810, name: 'Credit & Surety', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 24803, name: 'Credit & Surety', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 20108, name: 'Decenial', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 170203, name: 'Eastern Europe North', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 170205, name: 'Engineering', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 20101, name: 'France', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20105, name: 'French-Speaking Africa', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 50101, name: 'Germany', subsidiaryId: [5], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 100801, name: 'GS Pty Coinsur.', subsidiaryId: [10], subsidiaryLedger: 8, marketType: 'TRT'},
  {id: 200201, name: 'Hong-Kong', subsidiaryId: [20], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 200102, name: 'India', subsidiaryId: [20], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 201402, name: 'India Ledger', subsidiaryId: [20], subsidiaryLedger: 14, marketType: 'TRT'},
  {id: 20110, name: 'Inwards Retro', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 60101, name: 'Italy', subsidiaryId: [6], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 100101, name: 'Itasca', subsidiaryId: [10], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 100103, name: 'Itasca', subsidiaryId: [10], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 200401, name: 'Labuan', subsidiaryId: [20], subsidiaryLedger: 4, marketType: 'TRT'},
  {id: 174808, name: 'Marine', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 20113, name: 'MENA Retro', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 50105, name: 'MENA Retro', subsidiaryId: [5], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 101202, name: 'Miami', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'TRT'},
  {id: 101204, name: 'Miami PanLatAm', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'TRT'},
  {id: 20112, name: 'Middle-East', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 50103, name: 'Middle-East', subsidiaryId: [5], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20401, name: 'Moscow', subsidiaryId: [2], subsidiaryLedger: 4, marketType: 'TRT'},
  {id: 20107, name: 'Moscow C.E.I', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20104, name: 'Near-East', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20106, name: 'Netherlands', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 50106, name: 'Netherlands', subsidiaryId: [5], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20114, name: 'New Alternative Risk', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 200801, name: 'RETAKAFUL BUSINESS', subsidiaryId: [20], subsidiaryLedger: 8, marketType: 'TRT'},
  {id: 101201, name: 'Rio', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'TRT'},
  {id: 50102, name: 'Scandinavia', subsidiaryId: [5], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 200501, name: 'Seoul', subsidiaryId: [20], subsidiaryLedger: 5, marketType: 'TRT'},
  {id: 220201, name: 'Sidney', subsidiaryId: [22], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 200101, name: 'Singapore', subsidiaryId: [20], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20601, name: 'South-Africa', subsidiaryId: [2], subsidiaryLedger: 6, marketType: 'TRT'},
  {id: 50104, name: 'South-Eastern Europe', subsidiaryId: [5], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20201, name: 'Spain/Port.', subsidiaryId: [2], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 200202, name: 'Specialty Hong-Kong', subsidiaryId: [20], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 20602, name: 'Specialty Joburg', subsidiaryId: [2], subsidiaryLedger: 6, marketType: 'SPL'},
  {id: 20202, name: 'Specialty Madrid', subsidiaryId: [2], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 101206, name: 'Specialty Miami', subsidiaryId: [10], subsidiaryLedger: 12, marketType: 'SPL'},
  {id: 60102, name: 'Specialty Milan', subsidiaryId: [6], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 20109, name: 'Specialty Paris', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 200103, name: 'Specialty Singapore', subsidiaryId: [20], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 110102, name: 'Specialty Toronto', subsidiaryId: [11], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 100102, name: 'Specialty US', subsidiaryId: [10], subsidiaryLedger: 1, marketType: 'SPL'},
  {id: 174812, name: 'SRT', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'SPL'},
  {id: 11101, name: 'SUL UK', subsidiaryId: [1], subsidiaryLedger: 11, marketType: 'SPL'},
  {id: 11201, name: 'SUL UK Channel', subsidiaryId: [1], subsidiaryLedger: 12, marketType: 'SPL'},
  {id: 11301, name: 'SUL UK Non-Channel', subsidiaryId: [1], subsidiaryLedger: 13, marketType: 'SPL'},
  {id: 170201, name: 'Switzerland', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 101304, name: 'TEST 1', subsidiaryId: [10], subsidiaryLedger: 13, marketType: 'SPL'},
  {id: 100802, name: 'TEST 2', subsidiaryId: [10], subsidiaryLedger: 8, marketType: 'TRT'},
  {id: 200203, name: 'Tokyo', subsidiaryId: [20], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 110101, name: 'Toronto', subsidiaryId: [11], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 20103, name: 'Turkey', subsidiaryId: [2], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 10201, name: 'UK & Ireland', subsidiaryId: [1], subsidiaryLedger: 2, marketType: 'TRT'},
  {id: 104064, name: 'US Treaty team 2', subsidiaryId: [10], subsidiaryLedger: 8, marketType: 'TRT'},
  {id: 101407, name: 'US Treaty team 2bis', subsidiaryId: [10], subsidiaryLedger: 14, marketType: 'TRT'},
  {id: 104097, name: 'US-Mainland CAT Treaty', subsidiaryId: [10], subsidiaryLedger: 1, marketType: 'TRT'},
  {id: 170204, name: 'Zurich US CAT', subsidiaryId: [17], subsidiaryLedger: 2, marketType: 'TRT'}
];

const cedant = [
  {
    "id": 10000,
    "CLIENTNUMBER": 10000,
    "CLIENTSHORTNAME": "TAIPING RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10001,
    "CLIENTNUMBER": 10001,
    "CLIENTSHORTNAME": "LESLIE & GODWIN R/I LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10002,
    "CLIENTNUMBER": 10002,
    "CLIENTSHORTNAME": "PWS CROSS&BARNARD A/C&ADM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10003,
    "CLIENTNUMBER": 10003,
    "CLIENTSHORTNAME": "SEDGWICK R/I SERVICES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10004,
    "CLIENTNUMBER": 10004,
    "CLIENTSHORTNAME": "WIGHAM POLAND R/I (EWP)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10005,
    "CLIENTNUMBER": 10005,
    "CLIENTSHORTNAME": "ALLIANCE ASSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10006,
    "CLIENTNUMBER": 10006,
    "CLIENTSHORTNAME": "J&H MARSH WORLD SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10007,
    "CLIENTNUMBER": 10007,
    "CLIENTSHORTNAME": "HEATH FIELDING INS BKG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10008,
    "CLIENTNUMBER": 10008,
    "CLIENTSHORTNAME": "HEATH MARTENS HORNER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10009,
    "CLIENTNUMBER": 10009,
    "CLIENTSHORTNAME": "HEATH LAMBERT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10010,
    "CLIENTNUMBER": 10010,
    "CLIENTSHORTNAME": "MINET JH R/I BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10011,
    "CLIENTNUMBER": 10011,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10012,
    "CLIENTNUMBER": 10012,
    "CLIENTSHORTNAME": "SEDGWICK GROUP PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10013,
    "CLIENTNUMBER": 10013,
    "CLIENTSHORTNAME": "AXA RE UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10014,
    "CLIENTNUMBER": 10014,
    "CLIENTSHORTNAME": "XL LONDON MARKET LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10015,
    "CLIENTNUMBER": 10015,
    "CLIENTSHORTNAME": "SCOR CHANNEL LTD.",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10016,
    "CLIENTNUMBER": 10016,
    "CLIENTSHORTNAME": "BAVINA (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10017,
    "CLIENTNUMBER": 10017,
    "CLIENTSHORTNAME": "HEATH LAMBERT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10018,
    "CLIENTNUMBER": 10018,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10019,
    "CLIENTNUMBER": 10019,
    "CLIENTSHORTNAME": "CITICORP INS BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10020,
    "CLIENTNUMBER": 10020,
    "CLIENTSHORTNAME": "CUNNINGHAM IAP LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10021,
    "CLIENTNUMBER": 10021,
    "CLIENTSHORTNAME": "DAEHAN FIRE & MARINE INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10022,
    "CLIENTNUMBER": 10022,
    "CLIENTSHORTNAME": "DAI ICHI KYOTO R/I CO SA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10023,
    "CLIENTNUMBER": 10023,
    "CLIENTSHORTNAME": "DAI-TOKYO FIRE & MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10024,
    "CLIENTNUMBER": 10024,
    "CLIENTSHORTNAME": "JLT LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10025,
    "CLIENTNUMBER": 10025,
    "CLIENTSHORTNAME": "AON GERMANY DESK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10026,
    "CLIENTNUMBER": 10026,
    "CLIENTSHORTNAME": "KYOEI MUTUAL F&M",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10027,
    "CLIENTNUMBER": 10027,
    "CLIENTSHORTNAME": "NICHIDO F&M INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10028,
    "CLIENTNUMBER": 10028,
    "CLIENTSHORTNAME": "PICC  (LONDON OFFICE)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10029,
    "CLIENTNUMBER": 10029,
    "CLIENTSHORTNAME": "TAISEI F&M INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10030,
    "CLIENTNUMBER": 10030,
    "CLIENTSHORTNAME": "CRAWFORD & CO ADJUSTERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10031,
    "CLIENTNUMBER": 10031,
    "CLIENTSHORTNAME": "ZENKYOREN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10032,
    "CLIENTNUMBER": 10032,
    "CLIENTSHORTNAME": "CHINA RE LDN REP OFFICE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10033,
    "CLIENTNUMBER": 10033,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10034,
    "CLIENTNUMBER": 10034,
    "CLIENTSHORTNAME": "SEDGWICK ANALYSIS SER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10035,
    "CLIENTNUMBER": 10035,
    "CLIENTSHORTNAME": "WILLIS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10036,
    "CLIENTNUMBER": 10036,
    "CLIENTSHORTNAME": "THURGOOD FARMER & HACKETT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10037,
    "CLIENTNUMBER": 10037,
    "CLIENTSHORTNAME": "NELSON HURST GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10038,
    "CLIENTNUMBER": 10038,
    "CLIENTSHORTNAME": "ALLIANZ INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10039,
    "CLIENTNUMBER": 10039,
    "CLIENTSHORTNAME": "GENERAL ACCIDENT RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10040,
    "CLIENTNUMBER": 10040,
    "CLIENTSHORTNAME": "GIBBON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10041,
    "CLIENTNUMBER": 10041,
    "CLIENTSHORTNAME": "LEGAL & GENERAL INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10042,
    "CLIENTNUMBER": 10042,
    "CLIENTSHORTNAME": "LLOYD SYN.(K & R)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10043,
    "CLIENTNUMBER": 10043,
    "CLIENTSHORTNAME": "NEW ZEALAND SOUTH BRITISH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10044,
    "CLIENTNUMBER": 10044,
    "CLIENTSHORTNAME": "RELIANCE NATIONAL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10045,
    "CLIENTNUMBER": 10045,
    "CLIENTSHORTNAME": "WIGHAM POLAND&BEVINGTONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10046,
    "CLIENTNUMBER": 10046,
    "CLIENTSHORTNAME": "LLOYD SYN.(CLARKSON)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10047,
    "CLIENTNUMBER": 10047,
    "CLIENTSHORTNAME": "LLOYD SYN.(MUIR)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10048,
    "CLIENTNUMBER": 10048,
    "CLIENTSHORTNAME": "LLOYD'S SYNDICATE (J.&H.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10049,
    "CLIENTNUMBER": 10049,
    "CLIENTSHORTNAME": "LLOYD SYN.(U.F.R.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10050,
    "CLIENTNUMBER": 10050,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10051,
    "CLIENTNUMBER": 10051,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10052,
    "CLIENTNUMBER": 10052,
    "CLIENTSHORTNAME": "LLOYD SYN.(H.&G.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10053,
    "CLIENTNUMBER": 10053,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10054,
    "CLIENTNUMBER": 10054,
    "CLIENTSHORTNAME": "MOTORS INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10055,
    "CLIENTNUMBER": 10055,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10056,
    "CLIENTNUMBER": 10056,
    "CLIENTSHORTNAME": "INTERNATIONAL AVIATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10057,
    "CLIENTNUMBER": 10057,
    "CLIENTSHORTNAME": "JAGO - 205",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10058,
    "CLIENTNUMBER": 10058,
    "CLIENTSHORTNAME": "SUN ALLIANCE & LONDON INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10059,
    "CLIENTNUMBER": 10059,
    "CLIENTSHORTNAME": "FESTER FOTHERGILL HARTUNG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10060,
    "CLIENTNUMBER": 10060,
    "CLIENTSHORTNAME": "LONDON MASTER DRILLING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10061,
    "CLIENTNUMBER": 10061,
    "CLIENTSHORTNAME": "KORNER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10062,
    "CLIENTNUMBER": 10062,
    "CLIENTSHORTNAME": "STEN-RE (UK) LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10063,
    "CLIENTNUMBER": 10063,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10064,
    "CLIENTNUMBER": 10064,
    "CLIENTSHORTNAME": "LLOYD SYN.(A.SUISSE)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10065,
    "CLIENTNUMBER": 10065,
    "CLIENTSHORTNAME": "LLOYD SYN.(J. PUDD.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10066,
    "CLIENTNUMBER": 10066,
    "CLIENTSHORTNAME": "PITTS INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10067,
    "CLIENTNUMBER": 10067,
    "CLIENTSHORTNAME": "SOLAR UWTG AGENCIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10068,
    "CLIENTNUMBER": 10068,
    "CLIENTSHORTNAME": "PLYTAS ASSURANCES LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10069,
    "CLIENTNUMBER": 10069,
    "CLIENTSHORTNAME": "LLOYD SYN.(D.W.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10070,
    "CLIENTNUMBER": 10070,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10071,
    "CLIENTNUMBER": 10071,
    "CLIENTSHORTNAME": "LLOYD SYN.(W. F.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10072,
    "CLIENTNUMBER": 10072,
    "CLIENTSHORTNAME": "ANCON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10073,
    "CLIENTNUMBER": 10073,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10074,
    "CLIENTNUMBER": 10074,
    "CLIENTSHORTNAME": "LONDON AVIATION INS GRP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10075,
    "CLIENTNUMBER": 10075,
    "CLIENTSHORTNAME": "FINSKA SJOFORSAKRINGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10076,
    "CLIENTNUMBER": 10076,
    "CLIENTSHORTNAME": "CARVILL R K & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10077,
    "CLIENTNUMBER": 10077,
    "CLIENTSHORTNAME": "BEECHAM INS. LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10078,
    "CLIENTNUMBER": 10078,
    "CLIENTSHORTNAME": "GLAXO HOLDING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10079,
    "CLIENTNUMBER": 10079,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10080,
    "CLIENTNUMBER": 10080,
    "CLIENTSHORTNAME": "SEDGWICK MARINE LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10081,
    "CLIENTNUMBER": 10081,
    "CLIENTSHORTNAME": "DE NED LUCHTVAARTPOOL NV",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10082,
    "CLIENTNUMBER": 10082,
    "CLIENTSHORTNAME": "GROUPE BARTHELEMY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10083,
    "CLIENTNUMBER": 10083,
    "CLIENTSHORTNAME": "FENCHURCH R/I BKRS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10084,
    "CLIENTNUMBER": 10084,
    "CLIENTSHORTNAME": "GALLEON INS COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10085,
    "CLIENTNUMBER": 10085,
    "CLIENTSHORTNAME": "SOUTHGATE ASSOCIATES LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10086,
    "CLIENTNUMBER": 10086,
    "CLIENTSHORTNAME": "ALLIANZ INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10087,
    "CLIENTNUMBER": 10087,
    "CLIENTSHORTNAME": "LLOYD'S AT 190",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10088,
    "CLIENTNUMBER": 10088,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10089,
    "CLIENTNUMBER": 10089,
    "CLIENTSHORTNAME": "FIGRE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10090,
    "CLIENTNUMBER": 10090,
    "CLIENTSHORTNAME": "CX RE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10091,
    "CLIENTNUMBER": 10091,
    "CLIENTSHORTNAME": "POLYGON INSURANCE",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10092,
    "CLIENTNUMBER": 10092,
    "CLIENTSHORTNAME": "WILLIS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10093,
    "CLIENTNUMBER": 10093,
    "CLIENTSHORTNAME": "HANSA GENERAL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10094,
    "CLIENTNUMBER": 10094,
    "CLIENTSHORTNAME": "JARDINE INS BKRS INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10095,
    "CLIENTNUMBER": 10095,
    "CLIENTSHORTNAME": "AMTRUST EUROPE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10096,
    "CLIENTNUMBER": 10096,
    "CLIENTSHORTNAME": "AA HOMESURE INSURERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10097,
    "CLIENTNUMBER": 10097,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10098,
    "CLIENTNUMBER": 10098,
    "CLIENTSHORTNAME": "MOUNTGRAVE LTD.",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10099,
    "CLIENTNUMBER": 10099,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10100,
    "CLIENTNUMBER": 10100,
    "CLIENTSHORTNAME": "INDUSTRIAL RISK INSURERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10101,
    "CLIENTNUMBER": 10101,
    "CLIENTSHORTNAME": "ZURICH INT'L UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10102,
    "CLIENTNUMBER": 10102,
    "CLIENTSHORTNAME": "CRS INS SERVICES LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10103,
    "CLIENTNUMBER": 10103,
    "CLIENTSHORTNAME": "GEN ACCIDENT FIRE&LIFE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10104,
    "CLIENTNUMBER": 10104,
    "CLIENTSHORTNAME": "IPB",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10105,
    "CLIENTNUMBER": 10105,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10106,
    "CLIENTNUMBER": 10106,
    "CLIENTSHORTNAME": "CITY FIRE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10107,
    "CLIENTNUMBER": 10107,
    "CLIENTSHORTNAME": "BRITISH AEROSPACE INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10108,
    "CLIENTNUMBER": 10108,
    "CLIENTSHORTNAME": "VOLKSWAGEN INSURANCE COMP",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10109,
    "CLIENTNUMBER": 10109,
    "CLIENTSHORTNAME": "LLOYDS BER 539",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10110,
    "CLIENTNUMBER": 10110,
    "CLIENTSHORTNAME": "LLOYDS COT 538",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10111,
    "CLIENTNUMBER": 10111,
    "CLIENTSHORTNAME": "LLOYD'S VIA SYMONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10112,
    "CLIENTNUMBER": 10112,
    "CLIENTSHORTNAME": "BAVARIAN RE LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10113,
    "CLIENTNUMBER": 10113,
    "CLIENTSHORTNAME": "BRITISH COMMERCIAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10114,
    "CLIENTNUMBER": 10114,
    "CLIENTSHORTNAME": "AXA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10115,
    "CLIENTNUMBER": 10115,
    "CLIENTSHORTNAME": "WORLD AUXILIARY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10116,
    "CLIENTNUMBER": 10116,
    "CLIENTSHORTNAME": "SEA INSURANCE CO. LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10117,
    "CLIENTNUMBER": 10117,
    "CLIENTSHORTNAME": "LICENSES & GENERAL INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10118,
    "CLIENTNUMBER": 10118,
    "CLIENTSHORTNAME": "LONDON & OVERSEAS INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10119,
    "CLIENTNUMBER": 10119,
    "CLIENTSHORTNAME": "LEGAL & GENERAL ASS. SOC.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10120,
    "CLIENTNUMBER": 10120,
    "CLIENTSHORTNAME": "LLOYD'S MISE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10121,
    "CLIENTNUMBER": 10121,
    "CLIENTSHORTNAME": "LLOYD'S W.F.D.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10122,
    "CLIENTNUMBER": 10122,
    "CLIENTSHORTNAME": "NORTHERN ASS. CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10123,
    "CLIENTNUMBER": 10123,
    "CLIENTSHORTNAME": "GERLING GLOBAL RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10124,
    "CLIENTNUMBER": 10124,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10125,
    "CLIENTNUMBER": 10125,
    "CLIENTSHORTNAME": "THE INSURANCE CORP.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10126,
    "CLIENTNUMBER": 10126,
    "CLIENTSHORTNAME": "ENGLISH & AMERICAN INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10127,
    "CLIENTNUMBER": 10127,
    "CLIENTSHORTNAME": "HEATH CE (INS. SERV.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10128,
    "CLIENTNUMBER": 10128,
    "CLIENTSHORTNAME": "HIBERNIAN LIFE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10129,
    "CLIENTNUMBER": 10129,
    "CLIENTSHORTNAME": "TORO ASSICURAZIONI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10130,
    "CLIENTNUMBER": 10130,
    "CLIENTSHORTNAME": "BRITISH & OVERSEAS INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10131,
    "CLIENTNUMBER": 10131,
    "CLIENTSHORTNAME": "PRUDENTIAL ASSU CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10132,
    "CLIENTNUMBER": 10132,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10133,
    "CLIENTNUMBER": 10133,
    "CLIENTSHORTNAME": "EQUITABLE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10134,
    "CLIENTNUMBER": 10134,
    "CLIENTSHORTNAME": "EMPLOYERS LIABILITY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10135,
    "CLIENTNUMBER": 10135,
    "CLIENTSHORTNAME": "SOUTH BRITISH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10136,
    "CLIENTNUMBER": 10136,
    "CLIENTSHORTNAME": "NIG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10137,
    "CLIENTNUMBER": 10137,
    "CLIENTSHORTNAME": "INA R/I COMPANY UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10138,
    "CLIENTNUMBER": 10138,
    "CLIENTSHORTNAME": "NEW ZEALAND INS.CO(UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10139,
    "CLIENTNUMBER": 10139,
    "CLIENTSHORTNAME": "LONDON & LANCASHIRE INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10140,
    "CLIENTNUMBER": 10140,
    "CLIENTSHORTNAME": "SWISS RE LIFE & HEALTH LT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10141,
    "CLIENTNUMBER": 10141,
    "CLIENTSHORTNAME": "VICTORIA INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10142,
    "CLIENTNUMBER": 10142,
    "CLIENTSHORTNAME": "NW REINSURANCE CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10143,
    "CLIENTNUMBER": 10143,
    "CLIENTSHORTNAME": "RELIANCE FIRE & ACCIDENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10144,
    "CLIENTNUMBER": 10144,
    "CLIENTSHORTNAME": "UNITED STANDARD INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10145,
    "CLIENTNUMBER": 10145,
    "CLIENTSHORTNAME": "SHIELD INSURANCE CO. LTD.",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10146,
    "CLIENTNUMBER": 10146,
    "CLIENTSHORTNAME": "AVIATION & GENERAL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10147,
    "CLIENTNUMBER": 10147,
    "CLIENTSHORTNAME": "THE CONTINGENCY INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10148,
    "CLIENTNUMBER": 10148,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10149,
    "CLIENTNUMBER": 10149,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10150,
    "CLIENTNUMBER": 10150,
    "CLIENTSHORTNAME": "SPRINKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10151,
    "CLIENTNUMBER": 10151,
    "CLIENTSHORTNAME": "LLOYD'S LEBLANC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10152,
    "CLIENTNUMBER": 10152,
    "CLIENTSHORTNAME": "SWANN & EVERETT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10153,
    "CLIENTNUMBER": 10153,
    "CLIENTSHORTNAME": "LIVERPOOL & LDN & GLOBE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10154,
    "CLIENTNUMBER": 10154,
    "CLIENTSHORTNAME": "MERRICK INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10155,
    "CLIENTNUMBER": 10155,
    "CLIENTSHORTNAME": "NATIONAL NEW ZEALAND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10156,
    "CLIENTNUMBER": 10156,
    "CLIENTSHORTNAME": "SOUTH INDIA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10157,
    "CLIENTNUMBER": 10157,
    "CLIENTSHORTNAME": "SOUSC.WILLIS FAB.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10158,
    "CLIENTNUMBER": 10158,
    "CLIENTSHORTNAME": "MINSTER INSURANCE SERVICE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10159,
    "CLIENTNUMBER": 10159,
    "CLIENTSHORTNAME": "LLOYD'S STEWART S",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10160,
    "CLIENTNUMBER": 10160,
    "CLIENTSHORTNAME": "LLOYD'S BOLTON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10161,
    "CLIENTNUMBER": 10161,
    "CLIENTSHORTNAME": "CITY GENERAL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10162,
    "CLIENTNUMBER": 10162,
    "CLIENTSHORTNAME": "HOME & OVERSEAS INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10163,
    "CLIENTNUMBER": 10163,
    "CLIENTSHORTNAME": "AFIA REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10164,
    "CLIENTNUMBER": 10164,
    "CLIENTSHORTNAME": "ITT LONDON & EDINBURGH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10165,
    "CLIENTNUMBER": 10165,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10166,
    "CLIENTNUMBER": 10166,
    "CLIENTSHORTNAME": "DOMINION INS COMPANY L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10167,
    "CLIENTNUMBER": 10167,
    "CLIENTSHORTNAME": "GENERAL RE SYNDICATE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10168,
    "CLIENTNUMBER": 10168,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10169,
    "CLIENTNUMBER": 10169,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10170,
    "CLIENTNUMBER": 10170,
    "CLIENTSHORTNAME": "TRIDENT GENERAL INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10171,
    "CLIENTNUMBER": 10171,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10172,
    "CLIENTNUMBER": 10172,
    "CLIENTSHORTNAME": "ZURICH GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10173,
    "CLIENTNUMBER": 10173,
    "CLIENTSHORTNAME": "THE LEGAL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10174,
    "CLIENTNUMBER": 10174,
    "CLIENTSHORTNAME": "LONDON ASSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10175,
    "CLIENTNUMBER": 10175,
    "CLIENTSHORTNAME": "SOUS. ALEX. HOWDEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10176,
    "CLIENTNUMBER": 10176,
    "CLIENTSHORTNAME": "NATIONAL & GENERAL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10177,
    "CLIENTNUMBER": 10177,
    "CLIENTSHORTNAME": "ARPEL UWTG AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10178,
    "CLIENTNUMBER": 10178,
    "CLIENTSHORTNAME": "BRITISH MERCHANTS INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10179,
    "CLIENTNUMBER": 10179,
    "CLIENTSHORTNAME": "ARPEL J SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10180,
    "CLIENTNUMBER": 10180,
    "CLIENTSHORTNAME": "SYN JARD AMBR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10181,
    "CLIENTNUMBER": 10181,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10182,
    "CLIENTNUMBER": 10182,
    "CLIENTSHORTNAME": "SWISS NATIONAL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10183,
    "CLIENTNUMBER": 10183,
    "CLIENTSHORTNAME": "SYN KIRBY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10184,
    "CLIENTNUMBER": 10184,
    "CLIENTSHORTNAME": "BRITISH & EUROPEAN R/I",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10185,
    "CLIENTNUMBER": 10185,
    "CLIENTSHORTNAME": "CRUSADER INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10186,
    "CLIENTNUMBER": 10186,
    "CLIENTSHORTNAME": "ST HELEN'S INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10187,
    "CLIENTNUMBER": 10187,
    "CLIENTSHORTNAME": "SKANDINAVIA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10188,
    "CLIENTNUMBER": 10188,
    "CLIENTSHORTNAME": "LONDON & SCOTTISH ASSU",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10189,
    "CLIENTNUMBER": 10189,
    "CLIENTSHORTNAME": "LLOYD'S BLANC NIC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10190,
    "CLIENTNUMBER": 10190,
    "CLIENTSHORTNAME": "CFAU",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10191,
    "CLIENTNUMBER": 10191,
    "CLIENTSHORTNAME": "LLOYD'S MINET",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10192,
    "CLIENTNUMBER": 10192,
    "CLIENTSHORTNAME": "GOLDING CE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10193,
    "CLIENTNUMBER": 10193,
    "CLIENTSHORTNAME": "RELIANCE MARINE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10194,
    "CLIENTNUMBER": 10194,
    "CLIENTSHORTNAME": "THE YORKSHIRE INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10195,
    "CLIENTNUMBER": 10195,
    "CLIENTSHORTNAME": "LLOYD'S C.G.A.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10196,
    "CLIENTNUMBER": 10196,
    "CLIENTSHORTNAME": "BOLTON FB & NAMES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10197,
    "CLIENTNUMBER": 10197,
    "CLIENTSHORTNAME": "SCOTTISH INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10198,
    "CLIENTNUMBER": 10198,
    "CLIENTSHORTNAME": "BRITISH EUROP BAC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10199,
    "CLIENTNUMBER": 10199,
    "CLIENTSHORTNAME": "MATTHEWS WRIGHTSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10200,
    "CLIENTNUMBER": 10200,
    "CLIENTSHORTNAME": "PHOENIX FRANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10201,
    "CLIENTNUMBER": 10201,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10202,
    "CLIENTNUMBER": 10202,
    "CLIENTSHORTNAME": "LLOYD'S BOWRING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10203,
    "CLIENTNUMBER": 10203,
    "CLIENTSHORTNAME": "SUN ALLIANCE INS GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10204,
    "CLIENTNUMBER": 10204,
    "CLIENTSHORTNAME": "NAVIGATORS & GENERAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10205,
    "CLIENTNUMBER": 10205,
    "CLIENTSHORTNAME": "TARIFF REINSURANCES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10206,
    "CLIENTNUMBER": 10206,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10207,
    "CLIENTNUMBER": 10207,
    "CLIENTSHORTNAME": "SYN 22",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10208,
    "CLIENTNUMBER": 10208,
    "CLIENTSHORTNAME": "STRONGHOLD INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10209,
    "CLIENTNUMBER": 10209,
    "CLIENTSHORTNAME": "LLOYD'S PRICE FOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10210,
    "CLIENTNUMBER": 10210,
    "CLIENTSHORTNAME": "NORTHERN B",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10211,
    "CLIENTNUMBER": 10211,
    "CLIENTSHORTNAME": "INDEPENDENT INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10212,
    "CLIENTNUMBER": 10212,
    "CLIENTSHORTNAME": "LLOYD'S BBC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10213,
    "CLIENTNUMBER": 10213,
    "CLIENTSHORTNAME": "SPHERE INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10214,
    "CLIENTNUMBER": 10214,
    "CLIENTSHORTNAME": "MIDLAND ASSURANCE LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10215,
    "CLIENTNUMBER": 10215,
    "CLIENTSHORTNAME": "LLOYD'S A HOWDEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10216,
    "CLIENTNUMBER": 10216,
    "CLIENTSHORTNAME": "ROWBOTHAM RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10217,
    "CLIENTNUMBER": 10217,
    "CLIENTSHORTNAME": "GIBBON RW & SON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10218,
    "CLIENTNUMBER": 10218,
    "CLIENTSHORTNAME": "MOTOR UNION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10219,
    "CLIENTNUMBER": 10219,
    "CLIENTSHORTNAME": "INDEMNITY GUARANTEE ASSU",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10220,
    "CLIENTNUMBER": 10220,
    "CLIENTSHORTNAME": "GUARDIAN B ACC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10221,
    "CLIENTNUMBER": 10221,
    "CLIENTSHORTNAME": "GENERALI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10222,
    "CLIENTNUMBER": 10222,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10223,
    "CLIENTNUMBER": 10223,
    "CLIENTSHORTNAME": "RE GROUP MANAGERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10224,
    "CLIENTNUMBER": 10224,
    "CLIENTSHORTNAME": "WINCHESTER FOX & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10225,
    "CLIENTNUMBER": 10225,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10226,
    "CLIENTNUMBER": 10226,
    "CLIENTSHORTNAME": "LLOYDS GRZYBOWSKI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10227,
    "CLIENTNUMBER": 10227,
    "CLIENTSHORTNAME": "PACIFIC LIFE RE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10228,
    "CLIENTNUMBER": 10228,
    "CLIENTSHORTNAME": "BASTION INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10229,
    "CLIENTNUMBER": 10229,
    "CLIENTSHORTNAME": "SYN COLLINS 458",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10230,
    "CLIENTNUMBER": 10230,
    "CLIENTSHORTNAME": "SYN CLOSE SMITH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10231,
    "CLIENTNUMBER": 10231,
    "CLIENTSHORTNAME": "SYND MICHAEL DAVEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10232,
    "CLIENTNUMBER": 10232,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10233,
    "CLIENTNUMBER": 10233,
    "CLIENTSHORTNAME": "SYN HEATH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10234,
    "CLIENTNUMBER": 10234,
    "CLIENTSHORTNAME": "FODEN PATTINSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10235,
    "CLIENTNUMBER": 10235,
    "CLIENTSHORTNAME": "CORONET INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10236,
    "CLIENTNUMBER": 10236,
    "CLIENTSHORTNAME": "SYN EDMUNDS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10237,
    "CLIENTNUMBER": 10237,
    "CLIENTSHORTNAME": "SYN SALVESEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10238,
    "CLIENTNUMBER": 10238,
    "CLIENTSHORTNAME": "HOSE ET KRASEMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10239,
    "CLIENTNUMBER": 10239,
    "CLIENTSHORTNAME": "BOWRING BIATMC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10240,
    "CLIENTNUMBER": 10240,
    "CLIENTSHORTNAME": "TOWER UWTG MANAGEMENT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10241,
    "CLIENTNUMBER": 10241,
    "CLIENTSHORTNAME": "SOUSC.WIGHAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10242,
    "CLIENTNUMBER": 10242,
    "CLIENTSHORTNAME": "SOUSC BAIN DAWES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10243,
    "CLIENTNUMBER": 10243,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10244,
    "CLIENTNUMBER": 10244,
    "CLIENTSHORTNAME": "LLOYD'S MEACOCK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10245,
    "CLIENTNUMBER": 10245,
    "CLIENTSHORTNAME": "LLOYD'S SAFEGUARD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10246,
    "CLIENTNUMBER": 10246,
    "CLIENTSHORTNAME": "ARCHER ALLEN T",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10247,
    "CLIENTNUMBER": 10247,
    "CLIENTSHORTNAME": "LLOYD'S ASHLEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10248,
    "CLIENTNUMBER": 10248,
    "CLIENTSHORTNAME": "PETER CAMERON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10249,
    "CLIENTNUMBER": 10249,
    "CLIENTSHORTNAME": "IRAQ R/I COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10250,
    "CLIENTNUMBER": 10250,
    "CLIENTSHORTNAME": "SYN HOLDSWORTH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10251,
    "CLIENTNUMBER": 10251,
    "CLIENTSHORTNAME": "VEHICLE & GENERAL GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10252,
    "CLIENTNUMBER": 10252,
    "CLIENTSHORTNAME": "CORK WH GULLY & CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10253,
    "CLIENTNUMBER": 10253,
    "CLIENTSHORTNAME": "GARTHWAITE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10254,
    "CLIENTNUMBER": 10254,
    "CLIENTSHORTNAME": "SAMABYRGD ISLANDS",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 10255,
    "CLIENTNUMBER": 10255,
    "CLIENTSHORTNAME": "LLOYD'S HAMMOND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10256,
    "CLIENTNUMBER": 10256,
    "CLIENTSHORTNAME": "GRAY, DAWES, WESTRAY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10257,
    "CLIENTNUMBER": 10257,
    "CLIENTSHORTNAME": "SYN BROMLEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10258,
    "CLIENTNUMBER": 10258,
    "CLIENTSHORTNAME": "INT. GROUP P & I CLUBS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10259,
    "CLIENTNUMBER": 10259,
    "CLIENTSHORTNAME": "BRITISH NAT'L INS CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10260,
    "CLIENTNUMBER": 10260,
    "CLIENTSHORTNAME": "BENADY M AND CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10261,
    "CLIENTNUMBER": 10261,
    "CLIENTSHORTNAME": "LLOYD'S GLANVILL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10262,
    "CLIENTNUMBER": 10262,
    "CLIENTSHORTNAME": "SPHERE S ET E",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10263,
    "CLIENTNUMBER": 10263,
    "CLIENTSHORTNAME": "LLOYD'S NICHOLSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10264,
    "CLIENTNUMBER": 10264,
    "CLIENTSHORTNAME": "THE DAI-ICHI MUTUAL F&M",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10265,
    "CLIENTNUMBER": 10265,
    "CLIENTSHORTNAME": "RUTTY ME",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10266,
    "CLIENTNUMBER": 10266,
    "CLIENTSHORTNAME": "LLOYD'S I.R.M.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10267,
    "CLIENTNUMBER": 10267,
    "CLIENTSHORTNAME": "LLOYD'S SEDGW. PAYNE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10268,
    "CLIENTNUMBER": 10268,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10269,
    "CLIENTNUMBER": 10269,
    "CLIENTSHORTNAME": "LLOYD'S COURTIS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10270,
    "CLIENTNUMBER": 10270,
    "CLIENTSHORTNAME": "LLOYD'S BRADSTOCK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10271,
    "CLIENTNUMBER": 10271,
    "CLIENTSHORTNAME": "LLOYDS LAMBERT BR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10272,
    "CLIENTNUMBER": 10272,
    "CLIENTSHORTNAME": "NRG VICTORY REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10273,
    "CLIENTNUMBER": 10273,
    "CLIENTSHORTNAME": "SOUS. SEDGWICK PAYNE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10274,
    "CLIENTNUMBER": 10274,
    "CLIENTSHORTNAME": "DAVIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10275,
    "CLIENTNUMBER": 10275,
    "CLIENTSHORTNAME": "LLOYD'S SEDGWICK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10276,
    "CLIENTNUMBER": 10276,
    "CLIENTSHORTNAME": "PUBLISHERS & GENERAL INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10277,
    "CLIENTNUMBER": 10277,
    "CLIENTSHORTNAME": "WINCHESTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10278,
    "CLIENTNUMBER": 10278,
    "CLIENTSHORTNAME": "CO-OP INS CO AUST",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10279,
    "CLIENTNUMBER": 10279,
    "CLIENTSHORTNAME": "LLOYD'S BAIN SONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10280,
    "CLIENTNUMBER": 10280,
    "CLIENTSHORTNAME": "LIBERTY RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10281,
    "CLIENTNUMBER": 10281,
    "CLIENTSHORTNAME": "LLOYD'S CLARKSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10282,
    "CLIENTNUMBER": 10282,
    "CLIENTSHORTNAME": "LLOYDS BRADFORD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10283,
    "CLIENTNUMBER": 10283,
    "CLIENTSHORTNAME": "LEADENHALL INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10284,
    "CLIENTNUMBER": 10284,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10285,
    "CLIENTNUMBER": 10285,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10286,
    "CLIENTNUMBER": 10286,
    "CLIENTSHORTNAME": "NAL CASUA AMERICA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10287,
    "CLIENTNUMBER": 10287,
    "CLIENTSHORTNAME": "JAGO MANAGING AGENCY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10288,
    "CLIENTNUMBER": 10288,
    "CLIENTSHORTNAME": "REINSURANCE UNDERWRITING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10289,
    "CLIENTNUMBER": 10289,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10290,
    "CLIENTNUMBER": 10290,
    "CLIENTSHORTNAME": "LONDON OV FRANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10291,
    "CLIENTNUMBER": 10291,
    "CLIENTSHORTNAME": "RATTCLIFF SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10292,
    "CLIENTNUMBER": 10292,
    "CLIENTSHORTNAME": "LLOYD'S MAZZOTI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10293,
    "CLIENTNUMBER": 10293,
    "CLIENTSHORTNAME": "ALEXANDER R. DE ROUGEMONT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10294,
    "CLIENTNUMBER": 10294,
    "CLIENTSHORTNAME": "GROVES JOHN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10295,
    "CLIENTNUMBER": 10295,
    "CLIENTSHORTNAME": "LLOYD'S CHANDLER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10296,
    "CLIENTNUMBER": 10296,
    "CLIENTSHORTNAME": "GENERAL & EUROPEAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10297,
    "CLIENTNUMBER": 10297,
    "CLIENTSHORTNAME": "NV AMERSFOORTSE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10298,
    "CLIENTNUMBER": 10298,
    "CLIENTSHORTNAME": "LLOYDS HAMILTON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10299,
    "CLIENTNUMBER": 10299,
    "CLIENTSHORTNAME": "LLOYD'S LYON LOHR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10300,
    "CLIENTNUMBER": 10300,
    "CLIENTSHORTNAME": "TIMBER & GEN MUT ACCIDENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10301,
    "CLIENTNUMBER": 10301,
    "CLIENTSHORTNAME": "MONSIEUR NICK TYLER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10302,
    "CLIENTNUMBER": 10302,
    "CLIENTSHORTNAME": "ASTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10303,
    "CLIENTNUMBER": 10303,
    "CLIENTSHORTNAME": "LLOYD'S (SICOR)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10304,
    "CLIENTNUMBER": 10304,
    "CLIENTSHORTNAME": "GEORGE JONES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10305,
    "CLIENTNUMBER": 10305,
    "CLIENTSHORTNAME": "LLOYD'S SYMONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10306,
    "CLIENTNUMBER": 10306,
    "CLIENTSHORTNAME": "GUARDIAN CONRADI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10307,
    "CLIENTNUMBER": 10307,
    "CLIENTSHORTNAME": "NORWICH CONRADI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10308,
    "CLIENTNUMBER": 10308,
    "CLIENTSHORTNAME": "NRG LONDON REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10309,
    "CLIENTNUMBER": 10309,
    "CLIENTSHORTNAME": "SAMVINNUTRYGGINGAR MUTUAL",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 10310,
    "CLIENTNUMBER": 10310,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10311,
    "CLIENTNUMBER": 10311,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10312,
    "CLIENTNUMBER": 10312,
    "CLIENTSHORTNAME": "ROYAL EX STERLING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10313,
    "CLIENTNUMBER": 10313,
    "CLIENTSHORTNAME": "R/I CO. OF MAURITIUS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10314,
    "CLIENTNUMBER": 10314,
    "CLIENTSHORTNAME": "BLACK SEA THOMP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10315,
    "CLIENTNUMBER": 10315,
    "CLIENTSHORTNAME": "OVERSEAS UNION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10316,
    "CLIENTNUMBER": 10316,
    "CLIENTSHORTNAME": "LLOYDS HENRIJEAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10317,
    "CLIENTNUMBER": 10317,
    "CLIENTSHORTNAME": "LLOYD'S N.C.V.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10318,
    "CLIENTNUMBER": 10318,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10319,
    "CLIENTNUMBER": 10319,
    "CLIENTSHORTNAME": "LLOYD'S C.I.R.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10320,
    "CLIENTNUMBER": 10320,
    "CLIENTSHORTNAME": "SUN/OCEAN GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10321,
    "CLIENTNUMBER": 10321,
    "CLIENTSHORTNAME": "LLOYD'S BUCKENHAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10322,
    "CLIENTNUMBER": 10322,
    "CLIENTSHORTNAME": "NORTH ATLANTIC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10323,
    "CLIENTNUMBER": 10323,
    "CLIENTSHORTNAME": "EULER HERMES UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10324,
    "CLIENTNUMBER": 10324,
    "CLIENTSHORTNAME": "ANGLO FRENCH INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10325,
    "CLIENTNUMBER": 10325,
    "CLIENTSHORTNAME": "UNITED REINSURERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10326,
    "CLIENTNUMBER": 10326,
    "CLIENTSHORTNAME": "LLOYDS LOWNDES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10327,
    "CLIENTNUMBER": 10327,
    "CLIENTSHORTNAME": "LLOYDS GREIG FEST",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10328,
    "CLIENTNUMBER": 10328,
    "CLIENTSHORTNAME": "ZURICH SURETY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10329,
    "CLIENTNUMBER": 10329,
    "CLIENTSHORTNAME": "LLOYD HOLMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10330,
    "CLIENTNUMBER": 10330,
    "CLIENTSHORTNAME": "MARKEL INTERNATIONAL HQ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10331,
    "CLIENTNUMBER": 10331,
    "CLIENTSHORTNAME": "NATIONAL GENERAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10332,
    "CLIENTNUMBER": 10332,
    "CLIENTSHORTNAME": "ASSOC PROTECT IND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10333,
    "CLIENTNUMBER": 10333,
    "CLIENTSHORTNAME": "CULLUM AGENCIES LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10334,
    "CLIENTNUMBER": 10334,
    "CLIENTSHORTNAME": "PHOENIX CONTINENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10335,
    "CLIENTNUMBER": 10335,
    "CLIENTSHORTNAME": "AUTO FIRE GENERAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10336,
    "CLIENTNUMBER": 10336,
    "CLIENTSHORTNAME": "MARINE INSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10337,
    "CLIENTNUMBER": 10337,
    "CLIENTSHORTNAME": "SENTRY INDEMNITY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10338,
    "CLIENTNUMBER": 10338,
    "CLIENTSHORTNAME": "PHOENIX ESPAGNE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10339,
    "CLIENTNUMBER": 10339,
    "CLIENTSHORTNAME": "SOUSC. H S WEAVER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10340,
    "CLIENTNUMBER": 10340,
    "CLIENTSHORTNAME": "LLOYDS HOGG ROBIN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10341,
    "CLIENTNUMBER": 10341,
    "CLIENTSHORTNAME": "KNAPTON INS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10342,
    "CLIENTNUMBER": 10342,
    "CLIENTSHORTNAME": "SOUS LAVY HANCOX",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10343,
    "CLIENTNUMBER": 10343,
    "CLIENTSHORTNAME": "LLOYDS PAR WIGHAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10344,
    "CLIENTNUMBER": 10344,
    "CLIENTSHORTNAME": "HOWELL - 1093",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10345,
    "CLIENTNUMBER": 10345,
    "CLIENTSHORTNAME": "TRYGGING HF (INS LTD)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10346,
    "CLIENTNUMBER": 10346,
    "CLIENTSHORTNAME": "LLOYD BYAS MOSLEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10347,
    "CLIENTNUMBER": 10347,
    "CLIENTSHORTNAME": "FOUND WE SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10348,
    "CLIENTNUMBER": 10348,
    "CLIENTSHORTNAME": "INT UNDERWRITERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10349,
    "CLIENTNUMBER": 10349,
    "CLIENTSHORTNAME": "SLATER WALKER INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10350,
    "CLIENTNUMBER": 10350,
    "CLIENTSHORTNAME": "DOLPHIN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10351,
    "CLIENTNUMBER": 10351,
    "CLIENTSHORTNAME": "ENNIA IN COMPANY (U.K.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10352,
    "CLIENTNUMBER": 10352,
    "CLIENTSHORTNAME": "BRUNABOTAFELAG",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 10353,
    "CLIENTNUMBER": 10353,
    "CLIENTSHORTNAME": "PARIS EXCEL CONTR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10354,
    "CLIENTNUMBER": 10354,
    "CLIENTSHORTNAME": "SOUSC.GLANVILL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10355,
    "CLIENTNUMBER": 10355,
    "CLIENTSHORTNAME": "SOUSC.BAIN STEPH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10356,
    "CLIENTNUMBER": 10356,
    "CLIENTSHORTNAME": "LLOYD THOS MILLER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10357,
    "CLIENTNUMBER": 10357,
    "CLIENTSHORTNAME": "DORIC REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10358,
    "CLIENTNUMBER": 10358,
    "CLIENTSHORTNAME": "INS CORP IRELAND",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10359,
    "CLIENTNUMBER": 10359,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10360,
    "CLIENTNUMBER": 10360,
    "CLIENTSHORTNAME": "SOUSC.MEACOCK SAMUELSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10361,
    "CLIENTNUMBER": 10361,
    "CLIENTSHORTNAME": "LLOYD MORICE TOZE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10362,
    "CLIENTNUMBER": 10362,
    "CLIENTSHORTNAME": "GRINDLAY BRANDTS INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10363,
    "CLIENTNUMBER": 10363,
    "CLIENTSHORTNAME": "BAIN PROVINCIAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10364,
    "CLIENTNUMBER": 10364,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10365,
    "CLIENTNUMBER": 10365,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10366,
    "CLIENTNUMBER": 10366,
    "CLIENTSHORTNAME": "UNIONAMERICA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10367,
    "CLIENTNUMBER": 10367,
    "CLIENTSHORTNAME": "LLOYDS GOLDING A",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10368,
    "CLIENTNUMBER": 10368,
    "CLIENTSHORTNAME": "MARSH LTD AVIATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10369,
    "CLIENTNUMBER": 10369,
    "CLIENTSHORTNAME": "TANKER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10370,
    "CLIENTNUMBER": 10370,
    "CLIENTSHORTNAME": "HIGHLANDS INS. CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10371,
    "CLIENTNUMBER": 10371,
    "CLIENTSHORTNAME": "SOUSC.PEARSON WEB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10372,
    "CLIENTNUMBER": 10372,
    "CLIENTSHORTNAME": "BELL NICHOLSON HENDERSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10373,
    "CLIENTNUMBER": 10373,
    "CLIENTSHORTNAME": "ROSE THOMSON YOUNG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10374,
    "CLIENTNUMBER": 10374,
    "CLIENTSHORTNAME": "LION MOTOR SYND.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10375,
    "CLIENTNUMBER": 10375,
    "CLIENTSHORTNAME": "COWDY AD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10376,
    "CLIENTNUMBER": 10376,
    "CLIENTSHORTNAME": "SYN STURGE AC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10377,
    "CLIENTNUMBER": 10377,
    "CLIENTSHORTNAME": "GREEN - ?",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10378,
    "CLIENTNUMBER": 10378,
    "CLIENTSHORTNAME": "SYN HART CLR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10379,
    "CLIENTNUMBER": 10379,
    "CLIENTSHORTNAME": "SYND MARSH JS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10380,
    "CLIENTNUMBER": 10380,
    "CLIENTSHORTNAME": "SYN SPRATT WHITE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10381,
    "CLIENTNUMBER": 10381,
    "CLIENTSHORTNAME": "TOWNSEND SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10382,
    "CLIENTNUMBER": 10382,
    "CLIENTSHORTNAME": "LLOYDS L. GODWIN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10383,
    "CLIENTNUMBER": 10383,
    "CLIENTSHORTNAME": "LLOYDS BELL NICHO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10384,
    "CLIENTNUMBER": 10384,
    "CLIENTSHORTNAME": "STOREBRAND INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10385,
    "CLIENTNUMBER": 10385,
    "CLIENTSHORTNAME": "SYN QUATERMAINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10386,
    "CLIENTNUMBER": 10386,
    "CLIENTSHORTNAME": "SOUSC.BURGOYNE AL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10387,
    "CLIENTNUMBER": 10387,
    "CLIENTSHORTNAME": "SYN BRADING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10388,
    "CLIENTNUMBER": 10388,
    "CLIENTSHORTNAME": "ARROWFLEET MOTOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10389,
    "CLIENTNUMBER": 10389,
    "CLIENTSHORTNAME": "LLOYDS HEATH C.E.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10390,
    "CLIENTNUMBER": 10390,
    "CLIENTSHORTNAME": "SOUSC STEWART WRIGHTSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10391,
    "CLIENTNUMBER": 10391,
    "CLIENTSHORTNAME": "LLOYDS ROOKE TAYL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10392,
    "CLIENTNUMBER": 10392,
    "CLIENTSHORTNAME": "STEAMSHIP MUTUAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10393,
    "CLIENTNUMBER": 10393,
    "CLIENTSHORTNAME": "REYKVISK ENDURTRY",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 10394,
    "CLIENTNUMBER": 10394,
    "CLIENTSHORTNAME": "HARPER INS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10395,
    "CLIENTNUMBER": 10395,
    "CLIENTSHORTNAME": "LLOYDS SEDG. AVTN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10396,
    "CLIENTNUMBER": 10396,
    "CLIENTSHORTNAME": "LLOYDS BRADST AVN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10397,
    "CLIENTNUMBER": 10397,
    "CLIENTSHORTNAME": "SYN BOYAGIS JD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10398,
    "CLIENTNUMBER": 10398,
    "CLIENTSHORTNAME": "SIGNAL IMPERIAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10399,
    "CLIENTNUMBER": 10399,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10400,
    "CLIENTNUMBER": 10400,
    "CLIENTSHORTNAME": "HOME INS. D AC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10401,
    "CLIENTNUMBER": 10401,
    "CLIENTSHORTNAME": "SENTRY INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10402,
    "CLIENTNUMBER": 10402,
    "CLIENTSHORTNAME": "SYN SELLSRC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10403,
    "CLIENTNUMBER": 10403,
    "CLIENTSHORTNAME": "UNIONE ITALIANA UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10404,
    "CLIENTNUMBER": 10404,
    "CLIENTSHORTNAME": "COACHMAN MOTOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10405,
    "CLIENTNUMBER": 10405,
    "CLIENTSHORTNAME": "SOUSC.WESTMINSTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10406,
    "CLIENTNUMBER": 10406,
    "CLIENTSHORTNAME": "MENTOR INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10407,
    "CLIENTNUMBER": 10407,
    "CLIENTSHORTNAME": "SOUSC.TRIUMPH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10408,
    "CLIENTNUMBER": 10408,
    "CLIENTSHORTNAME": "L.GUARANTEE MINET",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10409,
    "CLIENTNUMBER": 10409,
    "CLIENTSHORTNAME": "SOUSC.W.FABER UER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10410,
    "CLIENTNUMBER": 10410,
    "CLIENTSHORTNAME": "SYN PAYNE NM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10411,
    "CLIENTNUMBER": 10411,
    "CLIENTSHORTNAME": "LLOYDS IRM FRANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10412,
    "CLIENTNUMBER": 10412,
    "CLIENTSHORTNAME": "BANKERS ASS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10413,
    "CLIENTNUMBER": 10413,
    "CLIENTSHORTNAME": "EULER HERMES GUARANTEE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10414,
    "CLIENTNUMBER": 10414,
    "CLIENTSHORTNAME": "MINET GLOBAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10415,
    "CLIENTNUMBER": 10415,
    "CLIENTSHORTNAME": "ENG & SCOTTISH MARITIME",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10416,
    "CLIENTNUMBER": 10416,
    "CLIENTSHORTNAME": "ALLSTATE INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10417,
    "CLIENTNUMBER": 10417,
    "CLIENTSHORTNAME": "CU RISK MANAGEMENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10418,
    "CLIENTNUMBER": 10418,
    "CLIENTSHORTNAME": "CONTINENTAL RE CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10419,
    "CLIENTNUMBER": 10419,
    "CLIENTSHORTNAME": "ARIEL - 48",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10420,
    "CLIENTNUMBER": 10420,
    "CLIENTSHORTNAME": "ENTERPRISE MOTOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10421,
    "CLIENTNUMBER": 10421,
    "CLIENTSHORTNAME": "EMERALD MOTOR POL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10422,
    "CLIENTNUMBER": 10422,
    "CLIENTSHORTNAME": "LLOYD FRIZZELL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10423,
    "CLIENTNUMBER": 10423,
    "CLIENTSHORTNAME": "GRACECHURCH INSURANCE CY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10424,
    "CLIENTNUMBER": 10424,
    "CLIENTSHORTNAME": "LLOYDS COVER R.40",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10425,
    "CLIENTNUMBER": 10425,
    "CLIENTSHORTNAME": "LLOYDS COVER R.45",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10426,
    "CLIENTNUMBER": 10426,
    "CLIENTSHORTNAME": "CONTINENTAL OF NYK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10427,
    "CLIENTNUMBER": 10427,
    "CLIENTSHORTNAME": "PINE TOP INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10428,
    "CLIENTNUMBER": 10428,
    "CLIENTSHORTNAME": "INT'L OIL INSURERS (IOI)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10429,
    "CLIENTNUMBER": 10429,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10430,
    "CLIENTNUMBER": 10430,
    "CLIENTSHORTNAME": "GA BONUS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10431,
    "CLIENTNUMBER": 10431,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": ""
  },
  {
    "id": 10432,
    "CLIENTNUMBER": 10432,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10433,
    "CLIENTNUMBER": 10433,
    "CLIENTSHORTNAME": "RIVER PLATE RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10434,
    "CLIENTNUMBER": 10434,
    "CLIENTSHORTNAME": "CONTI NAT AMER RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10435,
    "CLIENTNUMBER": 10435,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10436,
    "CLIENTNUMBER": 10436,
    "CLIENTSHORTNAME": "TRIDENT AVIA UFR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10437,
    "CLIENTNUMBER": 10437,
    "CLIENTSHORTNAME": "UNITED FRIENDLY GEN. INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10438,
    "CLIENTNUMBER": 10438,
    "CLIENTSHORTNAME": "DUBLIN RE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10439,
    "CLIENTNUMBER": 10439,
    "CLIENTSHORTNAME": "SYN STEWART BA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10440,
    "CLIENTNUMBER": 10440,
    "CLIENTSHORTNAME": "INTER HAIL SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10441,
    "CLIENTNUMBER": 10441,
    "CLIENTSHORTNAME": "TREATY REINSURANCES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10442,
    "CLIENTNUMBER": 10442,
    "CLIENTSHORTNAME": "LUXEMBOURG RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10443,
    "CLIENTNUMBER": 10443,
    "CLIENTSHORTNAME": "INST RESS DO BRASIL (IRB)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10444,
    "CLIENTNUMBER": 10444,
    "CLIENTSHORTNAME": "STETZEL THOMPSON NM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10445,
    "CLIENTNUMBER": 10445,
    "CLIENTSHORTNAME": "IRISH NAC'S FORBE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10446,
    "CLIENTNUMBER": 10446,
    "CLIENTSHORTNAME": "SENTRY UK S FORBE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10447,
    "CLIENTNUMBER": 10447,
    "CLIENTSHORTNAME": "SYN CLELLAND  DM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10448,
    "CLIENTNUMBER": 10448,
    "CLIENTSHORTNAME": "WEST SIDE SERV ST",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10449,
    "CLIENTNUMBER": 10449,
    "CLIENTSHORTNAME": "AIICO ME",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10450,
    "CLIENTNUMBER": 10450,
    "CLIENTSHORTNAME": "FEDERATION MUTUAL INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10451,
    "CLIENTNUMBER": 10451,
    "CLIENTSHORTNAME": "SYN COX LG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10452,
    "CLIENTNUMBER": 10452,
    "CLIENTSHORTNAME": "SYN HAYNES GA ESQ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10453,
    "CLIENTNUMBER": 10453,
    "CLIENTSHORTNAME": "SYND SPRATT MV",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10454,
    "CLIENTNUMBER": 10454,
    "CLIENTSHORTNAME": "NIGERIA REINSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10455,
    "CLIENTNUMBER": 10455,
    "CLIENTSHORTNAME": "GE FRANKONA REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10456,
    "CLIENTNUMBER": 10456,
    "CLIENTSHORTNAME": "IMPERIO REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10457,
    "CLIENTNUMBER": 10457,
    "CLIENTSHORTNAME": "DAVIES - 1021",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10458,
    "CLIENTNUMBER": 10458,
    "CLIENTSHORTNAME": "WESTMINSTER AVIATION INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10459,
    "CLIENTNUMBER": 10459,
    "CLIENTSHORTNAME": "DUMEDIN UWTG AGENCY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10460,
    "CLIENTNUMBER": 10460,
    "CLIENTSHORTNAME": "SOUS DONALD FOX & PART",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10461,
    "CLIENTNUMBER": 10461,
    "CLIENTSHORTNAME": "SAUDI GENERAL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10462,
    "CLIENTNUMBER": 10462,
    "CLIENTSHORTNAME": "R/I MANAGEMENT CORP. LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10463,
    "CLIENTNUMBER": 10463,
    "CLIENTSHORTNAME": "REGIS UWTG AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10464,
    "CLIENTNUMBER": 10464,
    "CLIENTSHORTNAME": "LYNE DA & CO LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10465,
    "CLIENTNUMBER": 10465,
    "CLIENTSHORTNAME": "CATALINA UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10466,
    "CLIENTNUMBER": 10466,
    "CLIENTSHORTNAME": "SOUSC.ANTHONY DEEN & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10467,
    "CLIENTNUMBER": 10467,
    "CLIENTSHORTNAME": "SOUS DAVID GALE LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10468,
    "CLIENTNUMBER": 10468,
    "CLIENTSHORTNAME": "LLOYD'S ANTONY GIBBS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10469,
    "CLIENTNUMBER": 10469,
    "CLIENTSHORTNAME": "SOUSC.ANTONY GIBBS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10470,
    "CLIENTNUMBER": 10470,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10471,
    "CLIENTNUMBER": 10471,
    "CLIENTSHORTNAME": "MEDITERRANEAN INS & R/I",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10472,
    "CLIENTNUMBER": 10472,
    "CLIENTSHORTNAME": "CROWE SYNDICATE MNGT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10473,
    "CLIENTNUMBER": 10473,
    "CLIENTSHORTNAME": "ANDREW DRYSDALE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10474,
    "CLIENTNUMBER": 10474,
    "CLIENTSHORTNAME": "LENNOX UWTG AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10475,
    "CLIENTNUMBER": 10475,
    "CLIENTSHORTNAME": "HAZELTINE W & ASSO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10476,
    "CLIENTNUMBER": 10476,
    "CLIENTSHORTNAME": "SOUSC.C.E.HEATH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10477,
    "CLIENTNUMBER": 10477,
    "CLIENTSHORTNAME": "SENTRY UWTG AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10478,
    "CLIENTNUMBER": 10478,
    "CLIENTSHORTNAME": "SEAL UWTG AGENCY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10479,
    "CLIENTNUMBER": 10479,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10480,
    "CLIENTNUMBER": 10480,
    "CLIENTSHORTNAME": "ICAROM",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10481,
    "CLIENTNUMBER": 10481,
    "CLIENTSHORTNAME": "ICAROM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10482,
    "CLIENTNUMBER": 10482,
    "CLIENTSHORTNAME": "CAYZER STEEL BKRS 1977",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10483,
    "CLIENTNUMBER": 10483,
    "CLIENTSHORTNAME": "SOUS.DASHWOOD WILD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10484,
    "CLIENTNUMBER": 10484,
    "CLIENTSHORTNAME": "SOUS.NORMAN BUTCHER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10485,
    "CLIENTNUMBER": 10485,
    "CLIENTSHORTNAME": "UNITED COMMERCIAL AGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10486,
    "CLIENTNUMBER": 10486,
    "CLIENTSHORTNAME": "TRINITY SQUARE SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10487,
    "CLIENTNUMBER": 10487,
    "CLIENTSHORTNAME": "ASSOCIATED INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10488,
    "CLIENTNUMBER": 10488,
    "CLIENTSHORTNAME": "UNITED R/I CO OF IRELAND",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10489,
    "CLIENTNUMBER": 10489,
    "CLIENTSHORTNAME": "SOUS. STEN-RE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10490,
    "CLIENTNUMBER": 10490,
    "CLIENTSHORTNAME": "GRIFFIN FACTORS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10491,
    "CLIENTNUMBER": 10491,
    "CLIENTSHORTNAME": "SOUS.OAKELEY VAUGHAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10492,
    "CLIENTNUMBER": 10492,
    "CLIENTSHORTNAME": "ARAB UNIVERSAL INS/RIENS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10493,
    "CLIENTNUMBER": 10493,
    "CLIENTSHORTNAME": "SOUSC. ROBERT BARROW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10494,
    "CLIENTNUMBER": 10494,
    "CLIENTSHORTNAME": "PAN ATLANTIC R/I CO.LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10495,
    "CLIENTNUMBER": 10495,
    "CLIENTSHORTNAME": "HILLCOT U/ING MGNT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10496,
    "CLIENTNUMBER": 10496,
    "CLIENTSHORTNAME": "AIG UK LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10497,
    "CLIENTNUMBER": 10497,
    "CLIENTSHORTNAME": "HARTFORD RE EUROPE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10498,
    "CLIENTNUMBER": 10498,
    "CLIENTSHORTNAME": "VARIOUS LLOYD'S SYND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10499,
    "CLIENTNUMBER": 10499,
    "CLIENTSHORTNAME": "LIBERTY INT'L R/I CO LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10500,
    "CLIENTNUMBER": 10500,
    "CLIENTSHORTNAME": "ARAB INS GROUP (ARIG)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10501,
    "CLIENTNUMBER": 10501,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10502,
    "CLIENTNUMBER": 10502,
    "CLIENTSHORTNAME": "FINLAND GEN INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10503,
    "CLIENTNUMBER": 10503,
    "CLIENTSHORTNAME": "CLAPHAM - 800",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10504,
    "CLIENTNUMBER": 10504,
    "CLIENTSHORTNAME": "CAIRNGORM INSURANCE LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10505,
    "CLIENTNUMBER": 10505,
    "CLIENTSHORTNAME": "HANSECO INS CO LTD LDN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10506,
    "CLIENTNUMBER": 10506,
    "CLIENTSHORTNAME": "BRYANSTON INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10507,
    "CLIENTNUMBER": 10507,
    "CLIENTSHORTNAME": "TORCHMARK R/I CO LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10508,
    "CLIENTNUMBER": 10508,
    "CLIENTSHORTNAME": "WOOLWORTH SERVICES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10509,
    "CLIENTNUMBER": 10509,
    "CLIENTSHORTNAME": "FREMONT INS CO (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10510,
    "CLIENTNUMBER": 10510,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10511,
    "CLIENTNUMBER": 10511,
    "CLIENTSHORTNAME": "CX RE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10512,
    "CLIENTNUMBER": 10512,
    "CLIENTSHORTNAME": "NEW DH FORREST & OTHS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10513,
    "CLIENTNUMBER": 10513,
    "CLIENTSHORTNAME": "NEXUS",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10514,
    "CLIENTNUMBER": 10514,
    "CLIENTSHORTNAME": "ENCON UNDERWRITING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10515,
    "CLIENTNUMBER": 10515,
    "CLIENTSHORTNAME": "SJOVA-ALMENNAR",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10516,
    "CLIENTNUMBER": 10516,
    "CLIENTSHORTNAME": "CHANCELLOR INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10517,
    "CLIENTNUMBER": 10517,
    "CLIENTSHORTNAME": "LA COUPE INS LIMITED",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10518,
    "CLIENTNUMBER": 10518,
    "CLIENTSHORTNAME": "LANDMARK INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10519,
    "CLIENTNUMBER": 10519,
    "CLIENTSHORTNAME": "BEECHAM INS.CO.LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10520,
    "CLIENTNUMBER": 10520,
    "CLIENTSHORTNAME": "ISIS INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10521,
    "CLIENTNUMBER": 10521,
    "CLIENTSHORTNAME": "HCCI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10522,
    "CLIENTNUMBER": 10522,
    "CLIENTSHORTNAME": "BAIN CLARKSON SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10523,
    "CLIENTNUMBER": 10523,
    "CLIENTSHORTNAME": "PINNACLE INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10524,
    "CLIENTNUMBER": 10524,
    "CLIENTSHORTNAME": "SARNIA INSURANCE CO",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10525,
    "CLIENTNUMBER": 10525,
    "CLIENTSHORTNAME": "PENINSULA INS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10526,
    "CLIENTNUMBER": 10526,
    "CLIENTSHORTNAME": "THE COMMUNICATOR INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10527,
    "CLIENTNUMBER": 10527,
    "CLIENTSHORTNAME": "BRITISH MARINE MUTUAL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10528,
    "CLIENTNUMBER": 10528,
    "CLIENTSHORTNAME": "LASMO INS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10529,
    "CLIENTNUMBER": 10529,
    "CLIENTSHORTNAME": "CONSOLIDATED EUROPEAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10530,
    "CLIENTNUMBER": 10530,
    "CLIENTSHORTNAME": "TRIUMPH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10531,
    "CLIENTNUMBER": 10531,
    "CLIENTSHORTNAME": "CEDAR INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10532,
    "CLIENTNUMBER": 10532,
    "CLIENTSHORTNAME": "CHANCEL INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10533,
    "CLIENTNUMBER": 10533,
    "CLIENTSHORTNAME": "FREEMAN - ?",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10534,
    "CLIENTNUMBER": 10534,
    "CLIENTSHORTNAME": "HIGGINS ESQ. & OTHER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10535,
    "CLIENTNUMBER": 10535,
    "CLIENTSHORTNAME": "LLOYD'S TSB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10536,
    "CLIENTNUMBER": 10536,
    "CLIENTSHORTNAME": "PYBUS INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10537,
    "CLIENTNUMBER": 10537,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10538,
    "CLIENTNUMBER": 10538,
    "CLIENTSHORTNAME": "QBE SYNDICATE 566",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10539,
    "CLIENTNUMBER": 10539,
    "CLIENTSHORTNAME": "CHUBB INSURANCE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10540,
    "CLIENTNUMBER": 10540,
    "CLIENTSHORTNAME": "SUN ALLIANCE IRELAND",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10541,
    "CLIENTNUMBER": 10541,
    "CLIENTSHORTNAME": "NEW YORK RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10542,
    "CLIENTNUMBER": 10542,
    "CLIENTSHORTNAME": "LLOYD'S LIFE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10543,
    "CLIENTNUMBER": 10543,
    "CLIENTSHORTNAME": "PITRON SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10544,
    "CLIENTNUMBER": 10544,
    "CLIENTSHORTNAME": "PHILIP D'AMBRUM/NEWMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10545,
    "CLIENTNUMBER": 10545,
    "CLIENTSHORTNAME": "ANCHOR LS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10546,
    "CLIENTNUMBER": 10546,
    "CLIENTSHORTNAME": "CRANMER - 763",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10547,
    "CLIENTNUMBER": 10547,
    "CLIENTSHORTNAME": "DAVEY FC SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10548,
    "CLIENTNUMBER": 10548,
    "CLIENTSHORTNAME": "SYN BAYDAY&/OR HOLMES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10549,
    "CLIENTNUMBER": 10549,
    "CLIENTSHORTNAME": "SYN FRAMPTON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10550,
    "CLIENTNUMBER": 10550,
    "CLIENTSHORTNAME": "SYN GLOVER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10551,
    "CLIENTNUMBER": 10551,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10552,
    "CLIENTNUMBER": 10552,
    "CLIENTSHORTNAME": "SYN JOHNSON LR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10553,
    "CLIENTNUMBER": 10553,
    "CLIENTSHORTNAME": "LLOYDS SYN MOLLER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10554,
    "CLIENTNUMBER": 10554,
    "CLIENTSHORTNAME": "LLOYDS SYN NEWITT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10555,
    "CLIENTNUMBER": 10555,
    "CLIENTSHORTNAME": "OSIRIS MARINE INSURANCE",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10556,
    "CLIENTNUMBER": 10556,
    "CLIENTSHORTNAME": "TITAN INS CO LTD",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10557,
    "CLIENTNUMBER": 10557,
    "CLIENTSHORTNAME": "INT'L INS BKRS OF AMERICA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10558,
    "CLIENTNUMBER": 10558,
    "CLIENTSHORTNAME": "ARION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10559,
    "CLIENTNUMBER": 10559,
    "CLIENTSHORTNAME": "ATLANTIC UNION INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10560,
    "CLIENTNUMBER": 10560,
    "CLIENTSHORTNAME": "CROSSWALL R/I CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10561,
    "CLIENTNUMBER": 10561,
    "CLIENTSHORTNAME": "FEDERATED EMPLOYERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10562,
    "CLIENTNUMBER": 10562,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10563,
    "CLIENTNUMBER": 10563,
    "CLIENTSHORTNAME": "INCORPORATED GENERAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10564,
    "CLIENTNUMBER": 10564,
    "CLIENTSHORTNAME": "IRISH REINSURANCE POOL",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10565,
    "CLIENTNUMBER": 10565,
    "CLIENTSHORTNAME": "ASTRON LLOYDS SYN 1058",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10566,
    "CLIENTNUMBER": 10566,
    "CLIENTSHORTNAME": "AINSWORTH LLOYD'S SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10567,
    "CLIENTNUMBER": 10567,
    "CLIENTSHORTNAME": "AMSTRONG LLOYDS SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10568,
    "CLIENTNUMBER": 10568,
    "CLIENTSHORTNAME": "BROTHERTON SYN NO.?",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10569,
    "CLIENTNUMBER": 10569,
    "CLIENTSHORTNAME": "DEW LR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10570,
    "CLIENTNUMBER": 10570,
    "CLIENTSHORTNAME": "J C GRAVES & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10571,
    "CLIENTNUMBER": 10571,
    "CLIENTSHORTNAME": "DICK-CLELAND AB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10572,
    "CLIENTNUMBER": 10572,
    "CLIENTSHORTNAME": "GRATTAN AHB SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10573,
    "CLIENTNUMBER": 10573,
    "CLIENTSHORTNAME": "FRANK GREEN LLOYD'S",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10574,
    "CLIENTNUMBER": 10574,
    "CLIENTSHORTNAME": "HARDY SYNDICATE 382",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10575,
    "CLIENTNUMBER": 10575,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10576,
    "CLIENTNUMBER": 10576,
    "CLIENTSHORTNAME": "HARDCASTLE SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10577,
    "CLIENTNUMBER": 10577,
    "CLIENTSHORTNAME": "LOVE DAY SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10578,
    "CLIENTNUMBER": 10578,
    "CLIENTSHORTNAME": "NIGHTINGALE SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10579,
    "CLIENTNUMBER": 10579,
    "CLIENTSHORTNAME": "HOPWOOD SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10580,
    "CLIENTNUMBER": 10580,
    "CLIENTSHORTNAME": "KING LLOYD'S SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10581,
    "CLIENTNUMBER": 10581,
    "CLIENTSHORTNAME": "MASSEY RW/MAC KA JB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10582,
    "CLIENTNUMBER": 10582,
    "CLIENTSHORTNAME": "NELSON COURTAULDS SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10583,
    "CLIENTNUMBER": 10583,
    "CLIENTSHORTNAME": "POLLOCK DA SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10584,
    "CLIENTNUMBER": 10584,
    "CLIENTSHORTNAME": "PETER GREEN SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10585,
    "CLIENTNUMBER": 10585,
    "CLIENTSHORTNAME": "ROBERT MC SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10586,
    "CLIENTNUMBER": 10586,
    "CLIENTSHORTNAME": "ROUSE GN SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10587,
    "CLIENTNUMBER": 10587,
    "CLIENTSHORTNAME": "LLOYDS SYN RICE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10588,
    "CLIENTNUMBER": 10588,
    "CLIENTSHORTNAME": "LLOYDS ROKEBY-JOHNSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10589,
    "CLIENTNUMBER": 10589,
    "CLIENTSHORTNAME": "LLOYD'S SYN SYMES L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10590,
    "CLIENTNUMBER": 10590,
    "CLIENTSHORTNAME": "CASSIDY DAVIS SYN. MNGT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10591,
    "CLIENTNUMBER": 10591,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10592,
    "CLIENTNUMBER": 10592,
    "CLIENTSHORTNAME": "LLOYDS SYN WILSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10593,
    "CLIENTNUMBER": 10593,
    "CLIENTSHORTNAME": "PIJ ASS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10594,
    "CLIENTNUMBER": 10594,
    "CLIENTSHORTNAME": "LONDON SECURITY REINS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10595,
    "CLIENTNUMBER": 10595,
    "CLIENTSHORTNAME": "NEWGREEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10596,
    "CLIENTNUMBER": 10596,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10597,
    "CLIENTNUMBER": 10597,
    "CLIENTSHORTNAME": "R/I CORPORATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10598,
    "CLIENTNUMBER": 10598,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": ""
  },
  {
    "id": 10599,
    "CLIENTNUMBER": 10599,
    "CLIENTSHORTNAME": "STONE WALL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10600,
    "CLIENTNUMBER": 10600,
    "CLIENTSHORTNAME": "SUN INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10601,
    "CLIENTNUMBER": 10601,
    "CLIENTSHORTNAME": "COLONIAL MUTUAL GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10602,
    "CLIENTNUMBER": 10602,
    "CLIENTSHORTNAME": "UNITED FRIENDLY INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10603,
    "CLIENTNUMBER": 10603,
    "CLIENTSHORTNAME": "ALLIED DUNBAR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 19
  },
  {
    "id": 10604,
    "CLIENTNUMBER": 10604,
    "CLIENTSHORTNAME": "LLOYDS BANKING GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10605,
    "CLIENTNUMBER": 10605,
    "CLIENTSHORTNAME": "HUB INTERNAT. CORPORATE",
    "HEADOFFICECITY": "USA",
    "CLIENTSUBSIDIARYCODE": 10
  },
  {
    "id": 10606,
    "CLIENTNUMBER": 10606,
    "CLIENTSHORTNAME": "ROYAL LIFE HOLDINGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10607,
    "CLIENTNUMBER": 10607,
    "CLIENTSHORTNAME": "HUB INTERNAT MIDWEST LTD",
    "HEADOFFICECITY": "USA",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10608,
    "CLIENTNUMBER": 10608,
    "CLIENTSHORTNAME": "NEW IRELAND",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10609,
    "CLIENTNUMBER": 10609,
    "CLIENTSHORTNAME": "SCOTTISH PROVIDENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10610,
    "CLIENTNUMBER": 10610,
    "CLIENTSHORTNAME": "NAT'L PROVIDENT INST",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10611,
    "CLIENTNUMBER": 10611,
    "CLIENTSHORTNAME": "MERCHANT INVESTORS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10612,
    "CLIENTNUMBER": 10612,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10613,
    "CLIENTNUMBER": 10613,
    "CLIENTSHORTNAME": "SUN LIFE OF CANADA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10614,
    "CLIENTNUMBER": 10614,
    "CLIENTSHORTNAME": "CCL FINANCIAL GROUP PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10615,
    "CLIENTNUMBER": 10615,
    "CLIENTSHORTNAME": "ARISIS LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10616,
    "CLIENTNUMBER": 10616,
    "CLIENTSHORTNAME": "POOL KIRMT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10617,
    "CLIENTNUMBER": 10617,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10618,
    "CLIENTNUMBER": 10618,
    "CLIENTSHORTNAME": "NEVITT JC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10619,
    "CLIENTNUMBER": 10619,
    "CLIENTSHORTNAME": "COLLINGS - 1035",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10620,
    "CLIENTNUMBER": 10620,
    "CLIENTSHORTNAME": "SRB SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10621,
    "CLIENTNUMBER": 10621,
    "CLIENTSHORTNAME": "MAC SCOTT - 401",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10622,
    "CLIENTNUMBER": 10622,
    "CLIENTSHORTNAME": "BATHURST AE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10623,
    "CLIENTNUMBER": 10623,
    "CLIENTSHORTNAME": "RYAN E",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10624,
    "CLIENTNUMBER": 10624,
    "CLIENTSHORTNAME": "KNIGHT - 831",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10625,
    "CLIENTNUMBER": 10625,
    "CLIENTSHORTNAME": "ATRIUM SYND 609/0570",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10626,
    "CLIENTNUMBER": 10626,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10627,
    "CLIENTNUMBER": 10627,
    "CLIENTSHORTNAME": "GIBB RH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10628,
    "CLIENTNUMBER": 10628,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10629,
    "CLIENTNUMBER": 10629,
    "CLIENTSHORTNAME": "BLAIR - 287",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10630,
    "CLIENTNUMBER": 10630,
    "CLIENTSHORTNAME": "ATHEL REINSURANCE CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10631,
    "CLIENTNUMBER": 10631,
    "CLIENTSHORTNAME": "NORSE REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10632,
    "CLIENTNUMBER": 10632,
    "CLIENTSHORTNAME": "COPENHAGEN RE CO (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10633,
    "CLIENTNUMBER": 10633,
    "CLIENTSHORTNAME": "CHARTER R/I CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10634,
    "CLIENTNUMBER": 10634,
    "CLIENTSHORTNAME": "HANCOCK INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10635,
    "CLIENTNUMBER": 10635,
    "CLIENTSHORTNAME": "SAMPO INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10636,
    "CLIENTNUMBER": 10636,
    "CLIENTSHORTNAME": "ROSTRON HANCOCK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10637,
    "CLIENTNUMBER": 10637,
    "CLIENTSHORTNAME": "SCOTTISH MUTUAL ASSU",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10638,
    "CLIENTNUMBER": 10638,
    "CLIENTSHORTNAME": "NEW IRELAND",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10639,
    "CLIENTNUMBER": 10639,
    "CLIENTSHORTNAME": "LONDON&MANCHESTER ASSU",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10640,
    "CLIENTNUMBER": 10640,
    "CLIENTSHORTNAME": "PROSPERITY LIFE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10641,
    "CLIENTNUMBER": 10641,
    "CLIENTSHORTNAME": "SUN ALLIANCE FINANCIAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10642,
    "CLIENTNUMBER": 10642,
    "CLIENTSHORTNAME": "KOA INS (EUROPE)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10643,
    "CLIENTNUMBER": 10643,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10644,
    "CLIENTNUMBER": 10644,
    "CLIENTSHORTNAME": "GROVE - 561",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10645,
    "CLIENTNUMBER": 10645,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10646,
    "CLIENTNUMBER": 10646,
    "CLIENTSHORTNAME": "CITADEL R/I CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10647,
    "CLIENTNUMBER": 10647,
    "CLIENTSHORTNAME": "WASA INT'L (UK) INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10648,
    "CLIENTNUMBER": 10648,
    "CLIENTSHORTNAME": "NAC REINSURANCE CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10649,
    "CLIENTNUMBER": 10649,
    "CLIENTSHORTNAME": "WRIGHT UNDERWRITING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10650,
    "CLIENTNUMBER": 10650,
    "CLIENTSHORTNAME": "COMPAGNIE EUROPEENNE ASSU",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10651,
    "CLIENTNUMBER": 10651,
    "CLIENTSHORTNAME": "OCASO SA SEGUROS Y REAS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10652,
    "CLIENTNUMBER": 10652,
    "CLIENTSHORTNAME": "EMPLOYERS REINS.  INT.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10653,
    "CLIENTNUMBER": 10653,
    "CLIENTSHORTNAME": "FOLKSAM INT'L INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10654,
    "CLIENTNUMBER": 10654,
    "CLIENTSHORTNAME": "TRANSRE LONDON LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10655,
    "CLIENTNUMBER": 10655,
    "CLIENTSHORTNAME": "ARGENT - 113",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10656,
    "CLIENTNUMBER": 10656,
    "CLIENTSHORTNAME": "WEAVERS UWTG AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10657,
    "CLIENTNUMBER": 10657,
    "CLIENTSHORTNAME": "FINANCIAL GUARANTY INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10658,
    "CLIENTNUMBER": 10658,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10659,
    "CLIENTNUMBER": 10659,
    "CLIENTSHORTNAME": "MUTUAL REINSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10660,
    "CLIENTNUMBER": 10660,
    "CLIENTSHORTNAME": "INT'L INS CO OF HANNOVER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10661,
    "CLIENTNUMBER": 10661,
    "CLIENTSHORTNAME": "BALTICA INS COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10662,
    "CLIENTNUMBER": 10662,
    "CLIENTSHORTNAME": "CROMBIE INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10663,
    "CLIENTNUMBER": 10663,
    "CLIENTSHORTNAME": "STOCKHOLM R/I CO LTD UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10664,
    "CLIENTNUMBER": 10664,
    "CLIENTSHORTNAME": "HILLCOT RE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10665,
    "CLIENTNUMBER": 10665,
    "CLIENTSHORTNAME": "WALBROOK INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10666,
    "CLIENTNUMBER": 10666,
    "CLIENTSHORTNAME": "WAUSAU INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10667,
    "CLIENTNUMBER": 10667,
    "CLIENTSHORTNAME": "EL PASO INSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10668,
    "CLIENTNUMBER": 10668,
    "CLIENTSHORTNAME": "KINGSCROFT INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10669,
    "CLIENTNUMBER": 10669,
    "CLIENTSHORTNAME": "LIME STREET",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10670,
    "CLIENTNUMBER": 10670,
    "CLIENTSHORTNAME": "MARINE SHIPPING MUTUAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10671,
    "CLIENTNUMBER": 10671,
    "CLIENTSHORTNAME": "JENKS - 658",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10672,
    "CLIENTNUMBER": 10672,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10673,
    "CLIENTNUMBER": 10673,
    "CLIENTSHORTNAME": "BUTLER - 765",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10674,
    "CLIENTNUMBER": 10674,
    "CLIENTSHORTNAME": "SCAN RE INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10675,
    "CLIENTNUMBER": 10675,
    "CLIENTSHORTNAME": "HEDDINGTON INS (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10676,
    "CLIENTNUMBER": 10676,
    "CLIENTSHORTNAME": "PETROSWEDE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10677,
    "CLIENTNUMBER": 10677,
    "CLIENTSHORTNAME": "STRAKIS REASSU CONSULTANT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10678,
    "CLIENTNUMBER": 10678,
    "CLIENTSHORTNAME": "VAN OMMEREN LOGISTICS",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10679,
    "CLIENTNUMBER": 10679,
    "CLIENTSHORTNAME": "ATRADIUS UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10680,
    "CLIENTNUMBER": 10680,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10681,
    "CLIENTNUMBER": 10681,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10682,
    "CLIENTNUMBER": 10682,
    "CLIENTSHORTNAME": "AUSTRALIAN MUTUAL (AMP)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10683,
    "CLIENTNUMBER": 10683,
    "CLIENTSHORTNAME": "AGF HOLDING UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10684,
    "CLIENTNUMBER": 10684,
    "CLIENTSHORTNAME": "CLERICAL MEDICAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10685,
    "CLIENTNUMBER": 10685,
    "CLIENTSHORTNAME": "CROWN GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10686,
    "CLIENTNUMBER": 10686,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10687,
    "CLIENTNUMBER": 10687,
    "CLIENTSHORTNAME": "FAMILY ASSURANCE SOCIETY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10688,
    "CLIENTNUMBER": 10688,
    "CLIENTSHORTNAME": "FINANCIAL INSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10689,
    "CLIENTNUMBER": 10689,
    "CLIENTSHORTNAME": "DO NOT USE - DUPLICATE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": ""
  },
  {
    "id": 10690,
    "CLIENTNUMBER": 10690,
    "CLIENTSHORTNAME": "LAS INVESTMENT ASSU LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10691,
    "CLIENTNUMBER": 10691,
    "CLIENTSHORTNAME": "MANUFACTURERS LIFE INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10692,
    "CLIENTNUMBER": 10692,
    "CLIENTSHORTNAME": "PFM ASSU LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10693,
    "CLIENTNUMBER": 10693,
    "CLIENTSHORTNAME": "OLD MUTUAL LIFE UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10694,
    "CLIENTNUMBER": 10694,
    "CLIENTSHORTNAME": "SAVE & PROSPER INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10695,
    "CLIENTNUMBER": 10695,
    "CLIENTSHORTNAME": "TEACHERS ASSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10696,
    "CLIENTNUMBER": 10696,
    "CLIENTSHORTNAME": "FCP EURO ABS MEZZANINE",
    "HEADOFFICECITY": "FRA",
    "CLIENTSUBSIDIARYCODE": ""
  },
  {
    "id": 10697,
    "CLIENTNUMBER": 10697,
    "CLIENTSHORTNAME": "TOP-UK INSURANCE LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10698,
    "CLIENTNUMBER": 10698,
    "CLIENTSHORTNAME": "NRG FENCHURCH INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10699,
    "CLIENTNUMBER": 10699,
    "CLIENTSHORTNAME": "PAN FINANCIAL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10700,
    "CLIENTNUMBER": 10700,
    "CLIENTSHORTNAME": "LUDGATE INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10701,
    "CLIENTNUMBER": 10701,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10702,
    "CLIENTNUMBER": 10702,
    "CLIENTSHORTNAME": "DEVONPORT INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10703,
    "CLIENTNUMBER": 10703,
    "CLIENTSHORTNAME": "GTE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10704,
    "CLIENTNUMBER": 10704,
    "CLIENTSHORTNAME": "NORDEN INS COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10705,
    "CLIENTNUMBER": 10705,
    "CLIENTSHORTNAME": "STRAND INS CO LTD",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10706,
    "CLIENTNUMBER": 10706,
    "CLIENTSHORTNAME": "AJAX INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10707,
    "CLIENTNUMBER": 10707,
    "CLIENTSHORTNAME": "GANNET INS CO LIMITED",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10708,
    "CLIENTNUMBER": 10708,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10709,
    "CLIENTNUMBER": 10709,
    "CLIENTSHORTNAME": "AGRO INDUSTRIAL INS CO",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10710,
    "CLIENTNUMBER": 10710,
    "CLIENTSHORTNAME": "ASSITALIA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10711,
    "CLIENTNUMBER": 10711,
    "CLIENTSHORTNAME": "BERRY - 536",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10712,
    "CLIENTNUMBER": 10712,
    "CLIENTSHORTNAME": "DAVIES GRE & OTHS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10713,
    "CLIENTNUMBER": 10713,
    "CLIENTSHORTNAME": "PC INS COMPANY LIMITED",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10714,
    "CLIENTNUMBER": 10714,
    "CLIENTSHORTNAME": "NICHOLSON LESLIE AVIATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10715,
    "CLIENTNUMBER": 10715,
    "CLIENTSHORTNAME": "WOODSTOCK INSURANCE",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10716,
    "CLIENTNUMBER": 10716,
    "CLIENTSHORTNAME": "JESSEL - 512",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10717,
    "CLIENTNUMBER": 10717,
    "CLIENTSHORTNAME": "WORLD MARINE & GEN INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10718,
    "CLIENTNUMBER": 10718,
    "CLIENTSHORTNAME": "CHUBB INS CO OF EUROPE SE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10719,
    "CLIENTNUMBER": 10719,
    "CLIENTSHORTNAME": "ALEA",
    "HEADOFFICECITY": "JEY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10720,
    "CLIENTNUMBER": 10720,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10721,
    "CLIENTNUMBER": 10721,
    "CLIENTSHORTNAME": "SCOTTISH FRIENDLY ASS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10722,
    "CLIENTNUMBER": 10722,
    "CLIENTSHORTNAME": "ANGLO AMERICAN INS (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10723,
    "CLIENTNUMBER": 10723,
    "CLIENTSHORTNAME": "NORTH OF ENGLAND P&I ASS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10724,
    "CLIENTNUMBER": 10724,
    "CLIENTSHORTNAME": "METROPOLITAN RE CO (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10725,
    "CLIENTNUMBER": 10725,
    "CLIENTSHORTNAME": "MARINAIR INS COMPANY",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10726,
    "CLIENTNUMBER": 10726,
    "CLIENTSHORTNAME": "AACHEN MGMT SERVICES LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10727,
    "CLIENTNUMBER": 10727,
    "CLIENTSHORTNAME": "LIGNIN INS COMPANY",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10728,
    "CLIENTNUMBER": 10728,
    "CLIENTSHORTNAME": "ABB CAPTIVE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10729,
    "CLIENTNUMBER": 10729,
    "CLIENTSHORTNAME": "DUTCH-NORDIC INS CO N.V.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10730,
    "CLIENTNUMBER": 10730,
    "CLIENTSHORTNAME": "AMICAL INSURANCE LTD",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10731,
    "CLIENTNUMBER": 10731,
    "CLIENTSHORTNAME": "ASTRA ZENECA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10732,
    "CLIENTNUMBER": 10732,
    "CLIENTSHORTNAME": "TERRACE INSURANCES LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10733,
    "CLIENTNUMBER": 10733,
    "CLIENTSHORTNAME": "LONDON LIFE & GENERAL",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10734,
    "CLIENTNUMBER": 10734,
    "CLIENTSHORTNAME": "WELLINGTON MOTOR - 439",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10735,
    "CLIENTNUMBER": 10735,
    "CLIENTSHORTNAME": "SCANPORT",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10736,
    "CLIENTNUMBER": 10736,
    "CLIENTSHORTNAME": "IPC RE SERVICES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10737,
    "CLIENTNUMBER": 10737,
    "CLIENTSHORTNAME": "SCOR FICTIVE ROYAUME UNI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10738,
    "CLIENTNUMBER": 10738,
    "CLIENTSHORTNAME": "LLOYDS BAIN DAWES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10739,
    "CLIENTNUMBER": 10739,
    "CLIENTSHORTNAME": "LLOYDS TRAIL ATT.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10740,
    "CLIENTNUMBER": 10740,
    "CLIENTSHORTNAME": "ANDREW WEIR INS. CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10741,
    "CLIENTNUMBER": 10741,
    "CLIENTSHORTNAME": "LLOYDS ECKERSLEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10742,
    "CLIENTNUMBER": 10742,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10743,
    "CLIENTNUMBER": 10743,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10744,
    "CLIENTNUMBER": 10744,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10745,
    "CLIENTNUMBER": 10745,
    "CLIENTSHORTNAME": "REED STENHOUSE MARKETING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10746,
    "CLIENTNUMBER": 10746,
    "CLIENTSHORTNAME": "STENHOUSE LONDON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10747,
    "CLIENTNUMBER": 10747,
    "CLIENTSHORTNAME": "LLOYDS CHANDLER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10748,
    "CLIENTNUMBER": 10748,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10749,
    "CLIENTNUMBER": 10749,
    "CLIENTSHORTNAME": "LLOYDS HALF.SHEAD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10750,
    "CLIENTNUMBER": 10750,
    "CLIENTSHORTNAME": "CIS LIVESTOCK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10751,
    "CLIENTNUMBER": 10751,
    "CLIENTSHORTNAME": "LLOYDS WINCHESTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10752,
    "CLIENTNUMBER": 10752,
    "CLIENTSHORTNAME": "LLOYDS L.GODWIN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10753,
    "CLIENTNUMBER": 10753,
    "CLIENTSHORTNAME": "LLOYD PEARSON WEB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10754,
    "CLIENTNUMBER": 10754,
    "CLIENTSHORTNAME": "ANGLO SAXON INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10755,
    "CLIENTNUMBER": 10755,
    "CLIENTSHORTNAME": "MCLAUGHLIN SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10756,
    "CLIENTNUMBER": 10756,
    "CLIENTSHORTNAME": "LLOYDS FENCHURCH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10757,
    "CLIENTNUMBER": 10757,
    "CLIENTSHORTNAME": "LLOYDS GLANVILL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10758,
    "CLIENTNUMBER": 10758,
    "CLIENTSHORTNAME": "LLOYDS JARD.AMBR.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10759,
    "CLIENTNUMBER": 10759,
    "CLIENTSHORTNAME": "FENTON INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10760,
    "CLIENTNUMBER": 10760,
    "CLIENTSHORTNAME": "LLOYDS WIGHAM POL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10761,
    "CLIENTNUMBER": 10761,
    "CLIENTSHORTNAME": "LLOYDS (DASHWOOD)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10762,
    "CLIENTNUMBER": 10762,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10763,
    "CLIENTNUMBER": 10763,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10764,
    "CLIENTNUMBER": 10764,
    "CLIENTSHORTNAME": "SOUSC. BRADST.BL.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10765,
    "CLIENTNUMBER": 10765,
    "CLIENTSHORTNAME": "SOUSC. GRAY DAW.W",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10766,
    "CLIENTNUMBER": 10766,
    "CLIENTSHORTNAME": "SOUSC. G.IMBER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10767,
    "CLIENTNUMBER": 10767,
    "CLIENTSHORTNAME": "SOUSC.BELL NICHOL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10768,
    "CLIENTNUMBER": 10768,
    "CLIENTSHORTNAME": "NRG VICTORY RI LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10769,
    "CLIENTNUMBER": 10769,
    "CLIENTSHORTNAME": "LLOYDS AL.HOWDEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10770,
    "CLIENTNUMBER": 10770,
    "CLIENTSHORTNAME": "SYN WOOD BARDER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10771,
    "CLIENTNUMBER": 10771,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10772,
    "CLIENTNUMBER": 10772,
    "CLIENTSHORTNAME": "EDMUNDS RICHMOND LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10773,
    "CLIENTNUMBER": 10773,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10774,
    "CLIENTNUMBER": 10774,
    "CLIENTSHORTNAME": "SYN COCKELL MH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10775,
    "CLIENTNUMBER": 10775,
    "CLIENTSHORTNAME": "SOUSC. GROOM A.W.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10776,
    "CLIENTNUMBER": 10776,
    "CLIENTSHORTNAME": "SYN SASSE FR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10777,
    "CLIENTNUMBER": 10777,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10778,
    "CLIENTNUMBER": 10778,
    "CLIENTSHORTNAME": "SYN MILLER MG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10779,
    "CLIENTNUMBER": 10779,
    "CLIENTSHORTNAME": "SYN SHEAD AD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10780,
    "CLIENTNUMBER": 10780,
    "CLIENTSHORTNAME": "SYN BOYAGIS JD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10781,
    "CLIENTNUMBER": 10781,
    "CLIENTSHORTNAME": "SYN ROBERT RJM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10782,
    "CLIENTNUMBER": 10782,
    "CLIENTSHORTNAME": "SYN STEWART AB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10783,
    "CLIENTNUMBER": 10783,
    "CLIENTSHORTNAME": "MILES SMITH REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10784,
    "CLIENTNUMBER": 10784,
    "CLIENTSHORTNAME": "FRIZZELL SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10785,
    "CLIENTNUMBER": 10785,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10786,
    "CLIENTNUMBER": 10786,
    "CLIENTSHORTNAME": "LONDON GEN HOLDINGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10787,
    "CLIENTNUMBER": 10787,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10788,
    "CLIENTNUMBER": 10788,
    "CLIENTSHORTNAME": "ALLIANZ INT'L INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10789,
    "CLIENTNUMBER": 10789,
    "CLIENTSHORTNAME": "MARHAM CONSORTIUM MGMT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10790,
    "CLIENTNUMBER": 10790,
    "CLIENTSHORTNAME": "CGUUL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10791,
    "CLIENTNUMBER": 10791,
    "CLIENTSHORTNAME": "COLINS LIMITED",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10792,
    "CLIENTNUMBER": 10792,
    "CLIENTSHORTNAME": "AGROCHEM INS CO LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10793,
    "CLIENTNUMBER": 10793,
    "CLIENTSHORTNAME": "TOBACCO INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10794,
    "CLIENTNUMBER": 10794,
    "CLIENTSHORTNAME": "AIG EUROPE (UK) LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10795,
    "CLIENTNUMBER": 10795,
    "CLIENTSHORTNAME": "ARKWRIGHT INT'L LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10796,
    "CLIENTNUMBER": 10796,
    "CLIENTSHORTNAME": "SUN EAST DUBLIN CO LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10797,
    "CLIENTNUMBER": 10797,
    "CLIENTSHORTNAME": "SYDNEY INS & R/I",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10798,
    "CLIENTNUMBER": 10798,
    "CLIENTSHORTNAME": "PREFERRED DIRECT INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10799,
    "CLIENTNUMBER": 10799,
    "CLIENTSHORTNAME": "NORTH EUROPEAN R/I",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10800,
    "CLIENTNUMBER": 10800,
    "CLIENTSHORTNAME": "AXA COLONIA VERSICHERUNG",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10801,
    "CLIENTNUMBER": 10801,
    "CLIENTSHORTNAME": "WERLA INSURANCE CO LTD",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10802,
    "CLIENTNUMBER": 10802,
    "CLIENTSHORTNAME": "DOWNLANDS LIAB MNGMNT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10803,
    "CLIENTNUMBER": 10803,
    "CLIENTSHORTNAME": "COMPRE SERVICES (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10804,
    "CLIENTNUMBER": 10804,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10805,
    "CLIENTNUMBER": 10805,
    "CLIENTSHORTNAME": "BRADSTOCK BLUNT & THOMP.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10806,
    "CLIENTNUMBER": 10806,
    "CLIENTSHORTNAME": "MORICE TOZER BECK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10807,
    "CLIENTNUMBER": 10807,
    "CLIENTSHORTNAME": "LAMBERT HAMMOND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10808,
    "CLIENTNUMBER": 10808,
    "CLIENTSHORTNAME": "ROBINSON FRERE ROBINSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10809,
    "CLIENTNUMBER": 10809,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10810,
    "CLIENTNUMBER": 10810,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10811,
    "CLIENTNUMBER": 10811,
    "CLIENTSHORTNAME": "WITTS & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10812,
    "CLIENTNUMBER": 10812,
    "CLIENTSHORTNAME": "RICHARD OLIVER INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10813,
    "CLIENTNUMBER": 10813,
    "CLIENTSHORTNAME": "HUGHES-GIBB & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10814,
    "CLIENTNUMBER": 10814,
    "CLIENTSHORTNAME": "GOLDING ADAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10815,
    "CLIENTNUMBER": 10815,
    "CLIENTSHORTNAME": "KININMONTH PW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10816,
    "CLIENTNUMBER": 10816,
    "CLIENTSHORTNAME": "AON INSURANCE MANAGERS",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10817,
    "CLIENTNUMBER": 10817,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10818,
    "CLIENTNUMBER": 10818,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10819,
    "CLIENTNUMBER": 10819,
    "CLIENTSHORTNAME": "ENERGEX INT'L INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10820,
    "CLIENTNUMBER": 10820,
    "CLIENTSHORTNAME": "DE FALBE HALSEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10821,
    "CLIENTNUMBER": 10821,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10822,
    "CLIENTNUMBER": 10822,
    "CLIENTSHORTNAME": "ACCIDENT & HEALTH REINS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10823,
    "CLIENTNUMBER": 10823,
    "CLIENTSHORTNAME": "NELSON HURST R/I BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10824,
    "CLIENTNUMBER": 10824,
    "CLIENTSHORTNAME": "KAYE SON & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10825,
    "CLIENTNUMBER": 10825,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10826,
    "CLIENTNUMBER": 10826,
    "CLIENTSHORTNAME": "HAYWARD & CO LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10827,
    "CLIENTNUMBER": 10827,
    "CLIENTSHORTNAME": "ARBON LANGRISH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10828,
    "CLIENTNUMBER": 10828,
    "CLIENTSHORTNAME": "LOCHAIN PATRICK INS. BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10829,
    "CLIENTNUMBER": 10829,
    "CLIENTSHORTNAME": "LONMAR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10830,
    "CLIENTNUMBER": 10830,
    "CLIENTSHORTNAME": "HRH REINSURANCE BKRS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10831,
    "CLIENTNUMBER": 10831,
    "CLIENTSHORTNAME": "NICHOLSON LESLIE INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10832,
    "CLIENTNUMBER": 10832,
    "CLIENTSHORTNAME": "NICOLLS POINTING COULSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10833,
    "CLIENTNUMBER": 10833,
    "CLIENTSHORTNAME": "DEPENDABLE AGENCY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10834,
    "CLIENTNUMBER": 10834,
    "CLIENTSHORTNAME": "RASINI VIGANO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10835,
    "CLIENTNUMBER": 10835,
    "CLIENTSHORTNAME": "J&H MARSH GLOBAL BROKING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10836,
    "CLIENTNUMBER": 10836,
    "CLIENTSHORTNAME": "BEVINGTON VAIZEY & FOSTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10837,
    "CLIENTNUMBER": 10837,
    "CLIENTSHORTNAME": "ACIA (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10838,
    "CLIENTNUMBER": 10838,
    "CLIENTSHORTNAME": "MATHEWS MULCAHY",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10839,
    "CLIENTNUMBER": 10839,
    "CLIENTSHORTNAME": "CARROLL LONDON MARKETS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10840,
    "CLIENTNUMBER": 10840,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10841,
    "CLIENTNUMBER": 10841,
    "CLIENTSHORTNAME": "HINTON HILL INT,L LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10842,
    "CLIENTNUMBER": 10842,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10843,
    "CLIENTNUMBER": 10843,
    "CLIENTSHORTNAME": "GIL Y CARVAJAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10844,
    "CLIENTNUMBER": 10844,
    "CLIENTSHORTNAME": "WINDSOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10845,
    "CLIENTNUMBER": 10845,
    "CLIENTSHORTNAME": "NOREX IB LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10846,
    "CLIENTNUMBER": 10846,
    "CLIENTSHORTNAME": "NEW CENTURY VENTURES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10847,
    "CLIENTNUMBER": 10847,
    "CLIENTSHORTNAME": "STIRLING COOKE INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10848,
    "CLIENTNUMBER": 10848,
    "CLIENTSHORTNAME": "AEGIS INS BROKERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10849,
    "CLIENTNUMBER": 10849,
    "CLIENTSHORTNAME": "APS INT'L INS BRK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10850,
    "CLIENTNUMBER": 10850,
    "CLIENTSHORTNAME": "BRAITHWAITE AND ASSO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10851,
    "CLIENTNUMBER": 10851,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10852,
    "CLIENTNUMBER": 10852,
    "CLIENTSHORTNAME": "HWI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10853,
    "CLIENTNUMBER": 10853,
    "CLIENTSHORTNAME": "GRACECHURCH (LONDON) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10854,
    "CLIENTNUMBER": 10854,
    "CLIENTSHORTNAME": "INRE INTERNATIONAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10855,
    "CLIENTNUMBER": 10855,
    "CLIENTSHORTNAME": "SMALLWOOD TAYLOR UNITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10856,
    "CLIENTNUMBER": 10856,
    "CLIENTSHORTNAME": "ATKIN RAGGETT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10857,
    "CLIENTNUMBER": 10857,
    "CLIENTSHORTNAME": "HRQ AVIATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10858,
    "CLIENTNUMBER": 10858,
    "CLIENTSHORTNAME": "ROBERT BISHOP R/I CONS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10859,
    "CLIENTNUMBER": 10859,
    "CLIENTSHORTNAME": "HANSON ROBERTS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10860,
    "CLIENTNUMBER": 10860,
    "CLIENTSHORTNAME": "KNOTT AW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10861,
    "CLIENTNUMBER": 10861,
    "CLIENTSHORTNAME": "MARSH LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10862,
    "CLIENTNUMBER": 10862,
    "CLIENTSHORTNAME": "KROLLER & COMPANY (U.K.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10863,
    "CLIENTNUMBER": 10863,
    "CLIENTSHORTNAME": "JOHNSON & HIGGINS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10864,
    "CLIENTNUMBER": 10864,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10865,
    "CLIENTNUMBER": 10865,
    "CLIENTSHORTNAME": "BARCLAYS INS BRKS INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10866,
    "CLIENTNUMBER": 10866,
    "CLIENTSHORTNAME": "NASCO UK HOLDINGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10867,
    "CLIENTNUMBER": 10867,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10868,
    "CLIENTNUMBER": 10868,
    "CLIENTSHORTNAME": "NICHOLSON CHAMBERLAIN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10869,
    "CLIENTNUMBER": 10869,
    "CLIENTSHORTNAME": "BOSTON MARKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10870,
    "CLIENTNUMBER": 10870,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10871,
    "CLIENTNUMBER": 10871,
    "CLIENTSHORTNAME": "EUROPEAN RISK MGMT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10872,
    "CLIENTNUMBER": 10872,
    "CLIENTSHORTNAME": "UNIVERSAL RISK MGMT LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10873,
    "CLIENTNUMBER": 10873,
    "CLIENTSHORTNAME": "COMMUNITY REINSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10874,
    "CLIENTNUMBER": 10874,
    "CLIENTSHORTNAME": "VAP D'ENTREVES & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10875,
    "CLIENTNUMBER": 10875,
    "CLIENTSHORTNAME": "ALSFORD PAGE & GEMS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10876,
    "CLIENTNUMBER": 10876,
    "CLIENTSHORTNAME": "MANSON BYNG LTD R/I BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10877,
    "CLIENTNUMBER": 10877,
    "CLIENTSHORTNAME": "INTERNATIONAL INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10878,
    "CLIENTNUMBER": 10878,
    "CLIENTSHORTNAME": "HOLMAN WADE INS BROKERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10879,
    "CLIENTNUMBER": 10879,
    "CLIENTSHORTNAME": "ROOKE TAYLOR COOMBE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10880,
    "CLIENTNUMBER": 10880,
    "CLIENTSHORTNAME": "BATES EDWARD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10881,
    "CLIENTNUMBER": 10881,
    "CLIENTSHORTNAME": "ARMOUR HICK OTTEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10882,
    "CLIENTNUMBER": 10882,
    "CLIENTSHORTNAME": "BRANDTS INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10883,
    "CLIENTNUMBER": 10883,
    "CLIENTSHORTNAME": "MILLER ENERGY LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10884,
    "CLIENTNUMBER": 10884,
    "CLIENTSHORTNAME": "THOS R MILLER & SON INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10885,
    "CLIENTNUMBER": 10885,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10886,
    "CLIENTNUMBER": 10886,
    "CLIENTSHORTNAME": "WILLIS (EX COYLE HAM.)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10887,
    "CLIENTNUMBER": 10887,
    "CLIENTSHORTNAME": "TRAIL ATTENBOROUGH LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10888,
    "CLIENTNUMBER": 10888,
    "CLIENTSHORTNAME": "ROPNER INS. SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10889,
    "CLIENTNUMBER": 10889,
    "CLIENTSHORTNAME": "JOHN TOWNSEND & CO. LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10890,
    "CLIENTNUMBER": 10890,
    "CLIENTSHORTNAME": "PEARSON DALTON MORRIS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10891,
    "CLIENTNUMBER": 10891,
    "CLIENTSHORTNAME": "BYAS, MOSLEY & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10892,
    "CLIENTNUMBER": 10892,
    "CLIENTSHORTNAME": "BLACKWALL GREEN LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10893,
    "CLIENTNUMBER": 10893,
    "CLIENTSHORTNAME": "HEATH LAMBERT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10894,
    "CLIENTNUMBER": 10894,
    "CLIENTSHORTNAME": "EBERLI, SHORTER AND CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10895,
    "CLIENTNUMBER": 10895,
    "CLIENTSHORTNAME": "HEATH LAMBERT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10896,
    "CLIENTNUMBER": 10896,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10897,
    "CLIENTNUMBER": 10897,
    "CLIENTSHORTNAME": "HOWELL DO & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10898,
    "CLIENTNUMBER": 10898,
    "CLIENTSHORTNAME": "THB LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10899,
    "CLIENTNUMBER": 10899,
    "CLIENTSHORTNAME": "SHEAD AT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10900,
    "CLIENTNUMBER": 10900,
    "CLIENTSHORTNAME": "ROSE THOMSON YOUNG (R/I)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10901,
    "CLIENTNUMBER": 10901,
    "CLIENTSHORTNAME": "RAPID ADMIN LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10902,
    "CLIENTNUMBER": 10902,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10903,
    "CLIENTNUMBER": 10903,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10904,
    "CLIENTNUMBER": 10904,
    "CLIENTSHORTNAME": "NELSON HURST INTERN'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10905,
    "CLIENTNUMBER": 10905,
    "CLIENTSHORTNAME": "SEDGWICK COLLINS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10906,
    "CLIENTNUMBER": 10906,
    "CLIENTSHORTNAME": "HOGG ROBINSON & GARDNER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10907,
    "CLIENTNUMBER": 10907,
    "CLIENTSHORTNAME": "CHANDLER HARGREAVES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10908,
    "CLIENTNUMBER": 10908,
    "CLIENTSHORTNAME": "DURHAM HADLEY CANNON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10909,
    "CLIENTNUMBER": 10909,
    "CLIENTSHORTNAME": "INT'L R/I BROKERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10910,
    "CLIENTNUMBER": 10910,
    "CLIENTSHORTNAME": "ANGLO-SWISS INS & R/I",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10911,
    "CLIENTNUMBER": 10911,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10912,
    "CLIENTNUMBER": 10912,
    "CLIENTSHORTNAME": "KIRKLAND TIMMS LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10913,
    "CLIENTNUMBER": 10913,
    "CLIENTSHORTNAME": "BAIN HOGG LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10914,
    "CLIENTNUMBER": 10914,
    "CLIENTSHORTNAME": "BAIN DAWES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10915,
    "CLIENTNUMBER": 10915,
    "CLIENTSHORTNAME": "HALFORD, SHEAD & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10916,
    "CLIENTNUMBER": 10916,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10917,
    "CLIENTNUMBER": 10917,
    "CLIENTSHORTNAME": "CT BOWRING & MUIR BEDDALL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10918,
    "CLIENTNUMBER": 10918,
    "CLIENTSHORTNAME": "FRYER CHEASLEY LIGHT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10919,
    "CLIENTNUMBER": 10919,
    "CLIENTSHORTNAME": "STEWART WRIGHTSON INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10920,
    "CLIENTNUMBER": 10920,
    "CLIENTSHORTNAME": "NORMAN FRIZZELL&PART",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10921,
    "CLIENTNUMBER": 10921,
    "CLIENTSHORTNAME": "BAIN HOGG INTERNATIONAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10922,
    "CLIENTNUMBER": 10922,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10923,
    "CLIENTNUMBER": 10923,
    "CLIENTSHORTNAME": "LYON TRAILL ATTENBOROUGH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10924,
    "CLIENTNUMBER": 10924,
    "CLIENTSHORTNAME": "OVERSEAS R/I AGENCY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10925,
    "CLIENTNUMBER": 10925,
    "CLIENTSHORTNAME": "STENHOUSE SCOTT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10926,
    "CLIENTNUMBER": 10926,
    "CLIENTSHORTNAME": "OXENHAM G",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10927,
    "CLIENTNUMBER": 10927,
    "CLIENTSHORTNAME": "MUMMERY MORSE & RIMMER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10928,
    "CLIENTNUMBER": 10928,
    "CLIENTSHORTNAME": "MANN RUTTER & COLLINS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10929,
    "CLIENTNUMBER": 10929,
    "CLIENTSHORTNAME": "HOWDEN UK GROUP LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10930,
    "CLIENTNUMBER": 10930,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10931,
    "CLIENTNUMBER": 10931,
    "CLIENTSHORTNAME": "COLLINS AJ (R/I) LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10932,
    "CLIENTNUMBER": 10932,
    "CLIENTSHORTNAME": "HOBBS SAVILL & CO CFØ9851",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10933,
    "CLIENTNUMBER": 10933,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10934,
    "CLIENTNUMBER": 10934,
    "CLIENTSHORTNAME": "HUGH PAUL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10935,
    "CLIENTNUMBER": 10935,
    "CLIENTSHORTNAME": "BLEICHROEDER, BING & CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10936,
    "CLIENTNUMBER": 10936,
    "CLIENTSHORTNAME": "LAMBERT BROTHERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10937,
    "CLIENTNUMBER": 10937,
    "CLIENTSHORTNAME": "HEATH LAMBERT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10938,
    "CLIENTNUMBER": 10938,
    "CLIENTSHORTNAME": "HOLMWOODS BACK & MANSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10939,
    "CLIENTNUMBER": 10939,
    "CLIENTSHORTNAME": "RALLI BROTHERS (INS) LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10940,
    "CLIENTNUMBER": 10940,
    "CLIENTSHORTNAME": "TUFNELL SATTERTHWAITE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10941,
    "CLIENTNUMBER": 10941,
    "CLIENTSHORTNAME": "PICKFORD WATSN DAWSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10942,
    "CLIENTNUMBER": 10942,
    "CLIENTSHORTNAME": "HAMMOND L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10943,
    "CLIENTNUMBER": 10943,
    "CLIENTSHORTNAME": "AON UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10944,
    "CLIENTNUMBER": 10944,
    "CLIENTSHORTNAME": "ROSS COLLINS SPRINKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10945,
    "CLIENTNUMBER": 10945,
    "CLIENTSHORTNAME": "EIRB",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10946,
    "CLIENTNUMBER": 10946,
    "CLIENTSHORTNAME": "GOLDING STEWART WRIGHTSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10947,
    "CLIENTNUMBER": 10947,
    "CLIENTSHORTNAME": "ARGYLE O'SEAS (INS BKRS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10948,
    "CLIENTNUMBER": 10948,
    "CLIENTSHORTNAME": "PRICE FORBES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 10949,
    "CLIENTNUMBER": 10949,
    "CLIENTSHORTNAME": "BAIN SONS & GOLMICK LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10950,
    "CLIENTNUMBER": 10950,
    "CLIENTSHORTNAME": "HEATH CE CORP RISKS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10951,
    "CLIENTNUMBER": 10951,
    "CLIENTSHORTNAME": "HEATH OIL & GAS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10952,
    "CLIENTNUMBER": 10952,
    "CLIENTSHORTNAME": "DURHAM D G",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10953,
    "CLIENTNUMBER": 10953,
    "CLIENTSHORTNAME": "WFP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10954,
    "CLIENTNUMBER": 10954,
    "CLIENTSHORTNAME": "WINCHESTER BOWRING LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10955,
    "CLIENTNUMBER": 10955,
    "CLIENTSHORTNAME": "BANKERS TRUST COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10956,
    "CLIENTNUMBER": 10956,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10957,
    "CLIENTNUMBER": 10957,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10958,
    "CLIENTNUMBER": 10958,
    "CLIENTSHORTNAME": "CHANNEL ISLANDS INSURANCE",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10959,
    "CLIENTNUMBER": 10959,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10960,
    "CLIENTNUMBER": 10960,
    "CLIENTSHORTNAME": "TECNICAL & SPECIALIST SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10961,
    "CLIENTNUMBER": 10961,
    "CLIENTSHORTNAME": "AGRICULTURAL RISK MGMT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10962,
    "CLIENTNUMBER": 10962,
    "CLIENTSHORTNAME": "CRAWFORD TECH SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10963,
    "CLIENTNUMBER": 10963,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10964,
    "CLIENTNUMBER": 10964,
    "CLIENTSHORTNAME": "AON RISK  RESOURCES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10965,
    "CLIENTNUMBER": 10965,
    "CLIENTSHORTNAME": "ROBINS LONDON INT'L LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10966,
    "CLIENTNUMBER": 10966,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10967,
    "CLIENTNUMBER": 10967,
    "CLIENTSHORTNAME": "HYDROCARBON RISK CONSULT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10968,
    "CLIENTNUMBER": 10968,
    "CLIENTSHORTNAME": "ASSOCIATED RISK MGMT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10969,
    "CLIENTNUMBER": 10969,
    "CLIENTSHORTNAME": "BRITISH GAS PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10970,
    "CLIENTNUMBER": 10970,
    "CLIENTSHORTNAME": "IUA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10971,
    "CLIENTNUMBER": 10971,
    "CLIENTSHORTNAME": "HOLT - 46",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10972,
    "CLIENTNUMBER": 10972,
    "CLIENTSHORTNAME": "LLOYDS HUN 52",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10973,
    "CLIENTNUMBER": 10973,
    "CLIENTSHORTNAME": "LLOYDS GLR 55",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10974,
    "CLIENTNUMBER": 10974,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10975,
    "CLIENTNUMBER": 10975,
    "CLIENTSHORTNAME": "LLOYDS DCM 104",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10976,
    "CLIENTNUMBER": 10976,
    "CLIENTSHORTNAME": "AW STREET SYND 123/132",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10977,
    "CLIENTNUMBER": 10977,
    "CLIENTSHORTNAME": "LLOYDS GWP 164",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10978,
    "CLIENTNUMBER": 10978,
    "CLIENTSHORTNAME": "LLOYDS JEP 197",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10979,
    "CLIENTNUMBER": 10979,
    "CLIENTSHORTNAME": "LLOYDS HGJ 205",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10980,
    "CLIENTNUMBER": 10980,
    "CLIENTSHORTNAME": "LLOYDS ALS 206",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10981,
    "CLIENTNUMBER": 10981,
    "CLIENTSHORTNAME": "LLOYDS SMA 209",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10982,
    "CLIENTNUMBER": 10982,
    "CLIENTSHORTNAME": "LLOYDS CHB 216",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10983,
    "CLIENTNUMBER": 10983,
    "CLIENTSHORTNAME": "M A GRAVETT & OTHERS  227",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10984,
    "CLIENTNUMBER": 10984,
    "CLIENTSHORTNAME": "LLOYDS DJF 250",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10985,
    "CLIENTNUMBER": 10985,
    "CLIENTSHORTNAME": "LLOYDS NTB 255",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10986,
    "CLIENTNUMBER": 10986,
    "CLIENTSHORTNAME": "LLOYDS WHT 264",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10987,
    "CLIENTNUMBER": 10987,
    "CLIENTSHORTNAME": "LLOYDS MWC 268",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10988,
    "CLIENTNUMBER": 10988,
    "CLIENTSHORTNAME": "LLOYDS GJR 272",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10989,
    "CLIENTNUMBER": 10989,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10990,
    "CLIENTNUMBER": 10990,
    "CLIENTSHORTNAME": "LLOYDS DEC 288",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10991,
    "CLIENTNUMBER": 10991,
    "CLIENTSHORTNAME": "LLOYDS ALS 289",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10992,
    "CLIENTNUMBER": 10992,
    "CLIENTSHORTNAME": "LLOYDS DJW 290",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10993,
    "CLIENTNUMBER": 10993,
    "CLIENTSHORTNAME": "LLOYDS JJT 309",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10994,
    "CLIENTNUMBER": 10994,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10995,
    "CLIENTNUMBER": 10995,
    "CLIENTSHORTNAME": "LLOYDS TGG 321",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10996,
    "CLIENTNUMBER": 10996,
    "CLIENTSHORTNAME": "LLOYDS MCH 323",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10997,
    "CLIENTNUMBER": 10997,
    "CLIENTSHORTNAME": "LLOYDS MCC 340",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10998,
    "CLIENTNUMBER": 10998,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 10999,
    "CLIENTNUMBER": 10999,
    "CLIENTSHORTNAME": "LLOYDS SEC 367",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11000,
    "CLIENTNUMBER": 11000,
    "CLIENTSHORTNAME": "LLOYDS JHV 376",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11001,
    "CLIENTNUMBER": 11001,
    "CLIENTSHORTNAME": "LLOYDS SJE 384",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11002,
    "CLIENTNUMBER": 11002,
    "CLIENTSHORTNAME": "LLOYDS IAM 406",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11003,
    "CLIENTNUMBER": 11003,
    "CLIENTSHORTNAME": "LLOYDS POR 411",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11004,
    "CLIENTNUMBER": 11004,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11005,
    "CLIENTNUMBER": 11005,
    "CLIENTSHORTNAME": "LLOYDS DWP 421",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11006,
    "CLIENTNUMBER": 11006,
    "CLIENTSHORTNAME": "LLOYDS RFW 428",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11007,
    "CLIENTNUMBER": 11007,
    "CLIENTSHORTNAME": "LLOYDS GMT 445",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11008,
    "CLIENTNUMBER": 11008,
    "CLIENTSHORTNAME": "DUMAS - 448",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11009,
    "CLIENTNUMBER": 11009,
    "CLIENTSHORTNAME": "LLOYDS KAH 457",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11010,
    "CLIENTNUMBER": 11010,
    "CLIENTSHORTNAME": "LLOYDS PAT 464",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11011,
    "CLIENTNUMBER": 11011,
    "CLIENTSHORTNAME": "LLOYDS FJA 471",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11012,
    "CLIENTNUMBER": 11012,
    "CLIENTSHORTNAME": "LLOYDS JSM 484",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11013,
    "CLIENTNUMBER": 11013,
    "CLIENTSHORTNAME": "LLOYDS PDA 509",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11014,
    "CLIENTNUMBER": 11014,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11015,
    "CLIENTNUMBER": 11015,
    "CLIENTSHORTNAME": "LLOYDS SPR 546",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11016,
    "CLIENTNUMBER": 11016,
    "CLIENTSHORTNAME": "LLOYDS PGR 561",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11017,
    "CLIENTNUMBER": 11017,
    "CLIENTSHORTNAME": "LLOYDS STN 566",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11018,
    "CLIENTNUMBER": 11018,
    "CLIENTSHORTNAME": "LLOYDS AAC 582",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11019,
    "CLIENTNUMBER": 11019,
    "CLIENTSHORTNAME": "LLOYDS CWH 584",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11020,
    "CLIENTNUMBER": 11020,
    "CLIENTSHORTNAME": "LLOYDS KIM 601",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11021,
    "CLIENTNUMBER": 11021,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11022,
    "CLIENTNUMBER": 11022,
    "CLIENTSHORTNAME": "LLOYDS JLD 660",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11023,
    "CLIENTNUMBER": 11023,
    "CLIENTSHORTNAME": "LLOYDS RMO 661",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11024,
    "CLIENTNUMBER": 11024,
    "CLIENTSHORTNAME": "LLOYDS FWH 663",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11025,
    "CLIENTNUMBER": 11025,
    "CLIENTSHORTNAME": "LLOYDS DJM 666",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11026,
    "CLIENTNUMBER": 11026,
    "CLIENTSHORTNAME": "LLOYDS MCW 674",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11027,
    "CLIENTNUMBER": 11027,
    "CLIENTSHORTNAME": "LLOYDS LAM 694",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11028,
    "CLIENTNUMBER": 11028,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11029,
    "CLIENTNUMBER": 11029,
    "CLIENTSHORTNAME": "LLOYDS ECB 707",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11030,
    "CLIENTNUMBER": 11030,
    "CLIENTSHORTNAME": "LLOYDS BGA 709",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11031,
    "CLIENTNUMBER": 11031,
    "CLIENTSHORTNAME": "LLOYDS ARH 725",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11032,
    "CLIENTNUMBER": 11032,
    "CLIENTSHORTNAME": "LLOYDS EEP 726",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11033,
    "CLIENTNUMBER": 11033,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11034,
    "CLIENTNUMBER": 11034,
    "CLIENTSHORTNAME": "LLOYDS DGK 745",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11035,
    "CLIENTNUMBER": 11035,
    "CLIENTSHORTNAME": "LLOYDS JON 746",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11036,
    "CLIENTNUMBER": 11036,
    "CLIENTSHORTNAME": "LLOYDS KIN 748",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11037,
    "CLIENTNUMBER": 11037,
    "CLIENTSHORTNAME": "LLOYDS BFC 760",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11038,
    "CLIENTNUMBER": 11038,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11039,
    "CLIENTNUMBER": 11039,
    "CLIENTSHORTNAME": "LLOYDS FBB 787",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11040,
    "CLIENTNUMBER": 11040,
    "CLIENTSHORTNAME": "LLOYDS MOW 834",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11041,
    "CLIENTNUMBER": 11041,
    "CLIENTSHORTNAME": "LLOYDS RAF 836",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11042,
    "CLIENTNUMBER": 11042,
    "CLIENTSHORTNAME": "LLOYDS AMS 839",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11043,
    "CLIENTNUMBER": 11043,
    "CLIENTSHORTNAME": "LLOYDS RDP 896",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11044,
    "CLIENTNUMBER": 11044,
    "CLIENTSHORTNAME": "LLOYDS WAT 904",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11045,
    "CLIENTNUMBER": 11045,
    "CLIENTSHORTNAME": "LLOYDS FWH 961",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11046,
    "CLIENTNUMBER": 11046,
    "CLIENTSHORTNAME": "LLOYDS BAR 990",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11047,
    "CLIENTNUMBER": 11047,
    "CLIENTSHORTNAME": "LLOYDS BDK 994",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11048,
    "CLIENTNUMBER": 11048,
    "CLIENTSHORTNAME": "LLOYDS JRC 1001",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11049,
    "CLIENTNUMBER": 11049,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11050,
    "CLIENTNUMBER": 11050,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11051,
    "CLIENTNUMBER": 11051,
    "CLIENTSHORTNAME": "LLOYDS JWR 1005",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11052,
    "CLIENTNUMBER": 11052,
    "CLIENTSHORTNAME": "LLOYDS SVH 1007",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11053,
    "CLIENTNUMBER": 11053,
    "CLIENTSHORTNAME": "LLOYDS AJH 1009",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11054,
    "CLIENTNUMBER": 11054,
    "CLIENTSHORTNAME": "LLOYDS JCS 1011",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11055,
    "CLIENTNUMBER": 11055,
    "CLIENTSHORTNAME": "LLOYDS JHD 1021",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11056,
    "CLIENTNUMBER": 11056,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11057,
    "CLIENTNUMBER": 11057,
    "CLIENTSHORTNAME": "LLOYDS BJT 1049",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11058,
    "CLIENTNUMBER": 11058,
    "CLIENTSHORTNAME": "LLOYDS EJR 1051",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11059,
    "CLIENTNUMBER": 11059,
    "CLIENTSHORTNAME": "LLOYDS RRR 1055",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11060,
    "CLIENTNUMBER": 11060,
    "CLIENTSHORTNAME": "LLOYDS MEW 1069",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11061,
    "CLIENTNUMBER": 11061,
    "CLIENTSHORTNAME": "LLOYDS DSS 1081",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11062,
    "CLIENTNUMBER": 11062,
    "CLIENTSHORTNAME": "LLOYDS HAY 1084",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11063,
    "CLIENTNUMBER": 11063,
    "CLIENTSHORTNAME": "LLOYDS JNC 1087",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11064,
    "CLIENTNUMBER": 11064,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11065,
    "CLIENTNUMBER": 11065,
    "CLIENTSHORTNAME": "LLOYDS MVH 1093",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11066,
    "CLIENTNUMBER": 11066,
    "CLIENTSHORTNAME": "LLOYDS PTZ 1095",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11067,
    "CLIENTNUMBER": 11067,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11068,
    "CLIENTNUMBER": 11068,
    "CLIENTSHORTNAME": "LLOYDS TMJ 1097",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11069,
    "CLIENTNUMBER": 11069,
    "CLIENTSHORTNAME": "LLOYDS NVB 1114",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11070,
    "CLIENTNUMBER": 11070,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11071,
    "CLIENTNUMBER": 11071,
    "CLIENTSHORTNAME": "LLOYDS SHA 1125",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11072,
    "CLIENTNUMBER": 11072,
    "CLIENTSHORTNAME": "LLOYDS BOB 1129",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11073,
    "CLIENTNUMBER": 11073,
    "CLIENTSHORTNAME": "LLOYDS MER 1135",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11074,
    "CLIENTNUMBER": 11074,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11075,
    "CLIENTNUMBER": 11075,
    "CLIENTSHORTNAME": "LLOYDS JWN 1143",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11076,
    "CLIENTNUMBER": 11076,
    "CLIENTSHORTNAME": "LLOYDS CKL 1148",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11077,
    "CLIENTNUMBER": 11077,
    "CLIENTSHORTNAME": "LLOYDS RJJ 1163",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11078,
    "CLIENTNUMBER": 11078,
    "CLIENTSHORTNAME": "LLOYDS BUT 1179",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11079,
    "CLIENTNUMBER": 11079,
    "CLIENTSHORTNAME": "LLOYDS CBL 1182",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11080,
    "CLIENTNUMBER": 11080,
    "CLIENTSHORTNAME": "HOLMES JOHNSON LESSITER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11081,
    "CLIENTNUMBER": 11081,
    "CLIENTSHORTNAME": "ARMOUR HICK PARKER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11082,
    "CLIENTNUMBER": 11082,
    "CLIENTSHORTNAME": "AUSTENS R/I AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11083,
    "CLIENTNUMBER": 11083,
    "CLIENTSHORTNAME": "BLUETT SMITH & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11084,
    "CLIENTNUMBER": 11084,
    "CLIENTSHORTNAME": "BAYLY MARTIN & FAY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11085,
    "CLIENTNUMBER": 11085,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11086,
    "CLIENTNUMBER": 11086,
    "CLIENTSHORTNAME": "ROBERT BARROW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11087,
    "CLIENTNUMBER": 11087,
    "CLIENTSHORTNAME": "BELLEW PARRY & RAVEN LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11088,
    "CLIENTNUMBER": 11088,
    "CLIENTSHORTNAME": "BENNETT GOULD & PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11089,
    "CLIENTNUMBER": 11089,
    "CLIENTSHORTNAME": "ALFRED BLACKMORE & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11090,
    "CLIENTNUMBER": 11090,
    "CLIENTSHORTNAME": "SEDGWICK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11091,
    "CLIENTNUMBER": 11091,
    "CLIENTSHORTNAME": "SEDGWICK R/I BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11092,
    "CLIENTNUMBER": 11092,
    "CLIENTSHORTNAME": "F BOLTON INT LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11093,
    "CLIENTNUMBER": 11093,
    "CLIENTSHORTNAME": "F BOLTON MARINE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11094,
    "CLIENTNUMBER": 11094,
    "CLIENTSHORTNAME": "MARSH LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11095,
    "CLIENTNUMBER": 11095,
    "CLIENTSHORTNAME": "ROB BRADFORD HOBBS SAVILL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11096,
    "CLIENTNUMBER": 11096,
    "CLIENTSHORTNAME": "BRADSTOCK BLUNT & CRAWLEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11097,
    "CLIENTNUMBER": 11097,
    "CLIENTSHORTNAME": "PROMOTIONAL INS MGMT SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11098,
    "CLIENTNUMBER": 11098,
    "CLIENTSHORTNAME": "BRENTNALL BEARD INT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11099,
    "CLIENTNUMBER": 11099,
    "CLIENTSHORTNAME": "BRYANT & SHAW LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11100,
    "CLIENTNUMBER": 11100,
    "CLIENTSHORTNAME": "JK BUCKENHAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11101,
    "CLIENTNUMBER": 11101,
    "CLIENTSHORTNAME": "BURGOYNE ALFORD& CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11102,
    "CLIENTNUMBER": 11102,
    "CLIENTSHORTNAME": "BATESON & PAYNE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11103,
    "CLIENTNUMBER": 11103,
    "CLIENTSHORTNAME": "BOATWRIGHT JEWETT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11104,
    "CLIENTNUMBER": 11104,
    "CLIENTSHORTNAME": "BARKS WILLIAMS INS BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11105,
    "CLIENTNUMBER": 11105,
    "CLIENTSHORTNAME": "CARROLL RADFORD HOLDER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11106,
    "CLIENTNUMBER": 11106,
    "CLIENTSHORTNAME": "CARTER WILKES & FANE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11107,
    "CLIENTNUMBER": 11107,
    "CLIENTSHORTNAME": "CAYZER LEIGH & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11108,
    "CLIENTNUMBER": 11108,
    "CLIENTSHORTNAME": "GALLAGHER, READ&COLEMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11109,
    "CLIENTNUMBER": 11109,
    "CLIENTSHORTNAME": "MORGAN READ & SHARMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11110,
    "CLIENTNUMBER": 11110,
    "CLIENTSHORTNAME": "CAYZER STEEL BOWATER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11111,
    "CLIENTNUMBER": 11111,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11112,
    "CLIENTNUMBER": 11112,
    "CLIENTSHORTNAME": "COLBURN FRENCH AND KNEEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11113,
    "CLIENTNUMBER": 11113,
    "CLIENTSHORTNAME": "ED BROKING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11114,
    "CLIENTNUMBER": 11114,
    "CLIENTSHORTNAME": "BLANCH CRAWLEY WARREN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11115,
    "CLIENTNUMBER": 11115,
    "CLIENTSHORTNAME": "CROW DALTON LAMBERT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11116,
    "CLIENTNUMBER": 11116,
    "CLIENTSHORTNAME": "DASHWOOD BREWER PHIPPS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11117,
    "CLIENTNUMBER": 11117,
    "CLIENTSHORTNAME": "NELSON DONKIN & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11118,
    "CLIENTNUMBER": 11118,
    "CLIENTSHORTNAME": "WARD JD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11119,
    "CLIENTNUMBER": 11119,
    "CLIENTSHORTNAME": "SPECIAL CONTINGENCY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11120,
    "CLIENTNUMBER": 11120,
    "CLIENTSHORTNAME": "LANDER EBERLI SHORTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11121,
    "CLIENTNUMBER": 11121,
    "CLIENTSHORTNAME": "ECKERSLEY HICKS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11122,
    "CLIENTNUMBER": 11122,
    "CLIENTSHORTNAME": "EVANS-LOMBE ASHTON & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11123,
    "CLIENTNUMBER": 11123,
    "CLIENTSHORTNAME": "JOHNSON FRY INS SER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11124,
    "CLIENTNUMBER": 11124,
    "CLIENTSHORTNAME": "HEATH LAMBERT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11125,
    "CLIENTNUMBER": 11125,
    "CLIENTSHORTNAME": "FRIZZELL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11126,
    "CLIENTNUMBER": 11126,
    "CLIENTSHORTNAME": "HOULDER INS BKRS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11127,
    "CLIENTNUMBER": 11127,
    "CLIENTSHORTNAME": "PPM WORLDWIDE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11128,
    "CLIENTNUMBER": 11128,
    "CLIENTSHORTNAME": "GAULT ARMSTRONG KEMBLE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11129,
    "CLIENTNUMBER": 11129,
    "CLIENTSHORTNAME": "MARSH BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11130,
    "CLIENTNUMBER": 11130,
    "CLIENTSHORTNAME": "JLT UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11131,
    "CLIENTNUMBER": 11131,
    "CLIENTSHORTNAME": "GREENWAY INS BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11132,
    "CLIENTNUMBER": 11132,
    "CLIENTSHORTNAME": "GREIG FESTER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11133,
    "CLIENTNUMBER": 11133,
    "CLIENTSHORTNAME": "LOCKTON COMPANIES LLP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11134,
    "CLIENTNUMBER": 11134,
    "CLIENTSHORTNAME": "ALEC GUY & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11135,
    "CLIENTNUMBER": 11135,
    "CLIENTSHORTNAME": "GLENISTER CLEVERLEY & V",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11136,
    "CLIENTNUMBER": 11136,
    "CLIENTSHORTNAME": "GREENWOOD PRIEST & GREEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11137,
    "CLIENTNUMBER": 11137,
    "CLIENTSHORTNAME": "HADLEY CANNON LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11138,
    "CLIENTNUMBER": 11138,
    "CLIENTSHORTNAME": "CITY REACH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11139,
    "CLIENTNUMBER": 11139,
    "CLIENTSHORTNAME": "RML INTERNATIONAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11140,
    "CLIENTNUMBER": 11140,
    "CLIENTSHORTNAME": "BMS HARRIS & DIXON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11141,
    "CLIENTNUMBER": 11141,
    "CLIENTSHORTNAME": "HENRY HEAD AND CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11142,
    "CLIENTNUMBER": 11142,
    "CLIENTSHORTNAME": "HEATH CE (INT'L)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11143,
    "CLIENTNUMBER": 11143,
    "CLIENTSHORTNAME": "HEATH CE (NORTH AMERICA)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11144,
    "CLIENTNUMBER": 11144,
    "CLIENTSHORTNAME": "BAIN HOGG GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11145,
    "CLIENTNUMBER": 11145,
    "CLIENTSHORTNAME": "HORACE HOLMAN R/I BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11146,
    "CLIENTNUMBER": 11146,
    "CLIENTSHORTNAME": "JEWETT DUCHESNE (INT'L)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11147,
    "CLIENTNUMBER": 11147,
    "CLIENTSHORTNAME": "FMW INT'L INS. BRKS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11148,
    "CLIENTNUMBER": 11148,
    "CLIENTSHORTNAME": "HORNCASTLE CRAWFORD WEST",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11149,
    "CLIENTNUMBER": 11149,
    "CLIENTSHORTNAME": "HOUSLEY HEATH & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11150,
    "CLIENTNUMBER": 11150,
    "CLIENTSHORTNAME": "FINANCIAL INST. INS. BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11151,
    "CLIENTNUMBER": 11151,
    "CLIENTSHORTNAME": "HUTCHISON & CRAFT (LDN)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11152,
    "CLIENTNUMBER": 11152,
    "CLIENTSHORTNAME": "SALE TILNEY INTERSURE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11153,
    "CLIENTNUMBER": 11153,
    "CLIENTSHORTNAME": "JOHNSON PUDDIFOOT & LAST",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11154,
    "CLIENTNUMBER": 11154,
    "CLIENTSHORTNAME": "LANGMAN R J LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11155,
    "CLIENTNUMBER": 11155,
    "CLIENTSHORTNAME": "LESLIE & GODWIN LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11156,
    "CLIENTNUMBER": 11156,
    "CLIENTSHORTNAME": "EDWARD LUMLEY & SONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11157,
    "CLIENTNUMBER": 11157,
    "CLIENTSHORTNAME": "LYON DE FALBE (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11158,
    "CLIENTNUMBER": 11158,
    "CLIENTSHORTNAME": "LYON INT (C/O WINDSOR)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11159,
    "CLIENTNUMBER": 11159,
    "CLIENTSHORTNAME": "ANTHONY LUMSDEN & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11160,
    "CLIENTNUMBER": 11160,
    "CLIENTSHORTNAME": "CAMPION MCCALL LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11161,
    "CLIENTNUMBER": 11161,
    "CLIENTSHORTNAME": "MANSON BYNG AND CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11162,
    "CLIENTNUMBER": 11162,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11163,
    "CLIENTNUMBER": 11163,
    "CLIENTSHORTNAME": "EDINBURGH GEN INS SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11164,
    "CLIENTNUMBER": 11164,
    "CLIENTSHORTNAME": "MILES SMITH PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11165,
    "CLIENTNUMBER": 11165,
    "CLIENTSHORTNAME": "LOVAT INT'L LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11166,
    "CLIENTNUMBER": 11166,
    "CLIENTSHORTNAME": "MILLER INS SERVICES LLP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11167,
    "CLIENTNUMBER": 11167,
    "CLIENTSHORTNAME": "MINET JH AND CO LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11168,
    "CLIENTNUMBER": 11168,
    "CLIENTSHORTNAME": "CHRISTOPHER MORAN & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11169,
    "CLIENTNUMBER": 11169,
    "CLIENTSHORTNAME": "MOSSE PS & PART",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11170,
    "CLIENTNUMBER": 11170,
    "CLIENTSHORTNAME": "MCLAUGHLIN JB AND CO LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11171,
    "CLIENTNUMBER": 11171,
    "CLIENTSHORTNAME": "OAKELEY VAUGHAN & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11172,
    "CLIENTNUMBER": 11172,
    "CLIENTSHORTNAME": "PAUL BRADFORD & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11173,
    "CLIENTNUMBER": 11173,
    "CLIENTSHORTNAME": "TOZER PHILIPPS INT'L BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11174,
    "CLIENTNUMBER": 11174,
    "CLIENTSHORTNAME": "LPH PITMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11175,
    "CLIENTNUMBER": 11175,
    "CLIENTSHORTNAME": "PITMAN & DEANE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11176,
    "CLIENTNUMBER": 11176,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11177,
    "CLIENTNUMBER": 11177,
    "CLIENTSHORTNAME": "PWS NORTH AMERICA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11178,
    "CLIENTNUMBER": 11178,
    "CLIENTSHORTNAME": "REA BROTHERS (INS) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11179,
    "CLIENTNUMBER": 11179,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11180,
    "CLIENTNUMBER": 11180,
    "CLIENTSHORTNAME": "ROWBOTHAM&SONS INS BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11181,
    "CLIENTNUMBER": 11181,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11182,
    "CLIENTNUMBER": 11182,
    "CLIENTSHORTNAME": "RANKIN TINEY JAMES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11183,
    "CLIENTNUMBER": 11183,
    "CLIENTSHORTNAME": "SAMLER HAWKINS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11184,
    "CLIENTNUMBER": 11184,
    "CLIENTSHORTNAME": "SEASCOPE INS. SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11185,
    "CLIENTNUMBER": 11185,
    "CLIENTSHORTNAME": "KEITH SHIPION & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11186,
    "CLIENTNUMBER": 11186,
    "CLIENTSHORTNAME": "SMITH BILBROUGH & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11187,
    "CLIENTNUMBER": 11187,
    "CLIENTSHORTNAME": "STEWART WRIGHTSON NTH AM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11188,
    "CLIENTNUMBER": 11188,
    "CLIENTSHORTNAME": "CAPACITY RESOURCES LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11189,
    "CLIENTNUMBER": 11189,
    "CLIENTSHORTNAME": "STICKLAND WA & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11190,
    "CLIENTNUMBER": 11190,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11191,
    "CLIENTNUMBER": 11191,
    "CLIENTSHORTNAME": "SELBY R M & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11192,
    "CLIENTNUMBER": 11192,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11193,
    "CLIENTNUMBER": 11193,
    "CLIENTSHORTNAME": "STOIC INS BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11194,
    "CLIENTNUMBER": 11194,
    "CLIENTSHORTNAME": "ALEX FORBES MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11195,
    "CLIENTNUMBER": 11195,
    "CLIENTSHORTNAME": "TOWRY LAW (GEN INS) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11196,
    "CLIENTNUMBER": 11196,
    "CLIENTSHORTNAME": "LYON ATTENBOROUGH C/O PWS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11197,
    "CLIENTNUMBER": 11197,
    "CLIENTSHORTNAME": "TURNBULL GIBSON & CO BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11198,
    "CLIENTNUMBER": 11198,
    "CLIENTSHORTNAME": "GP TURNER & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11199,
    "CLIENTNUMBER": 11199,
    "CLIENTSHORTNAME": "HUTCHINSON FRENCH LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11200,
    "CLIENTNUMBER": 11200,
    "CLIENTSHORTNAME": "TYSERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11201,
    "CLIENTNUMBER": 11201,
    "CLIENTSHORTNAME": "TOMENSON SAUNDERS WHITEHD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11202,
    "CLIENTNUMBER": 11202,
    "CLIENTSHORTNAME": "TOWNSEND R/I BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11203,
    "CLIENTNUMBER": 11203,
    "CLIENTSHORTNAME": "VINCENT EWART & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11204,
    "CLIENTNUMBER": 11204,
    "CLIENTSHORTNAME": "WACKERBATH HARDMAN & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11205,
    "CLIENTNUMBER": 11205,
    "CLIENTSHORTNAME": "HALL HARFORD JEFFRIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11206,
    "CLIENTNUMBER": 11206,
    "CLIENTSHORTNAME": "WALROND SCARMAN AND CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11207,
    "CLIENTNUMBER": 11207,
    "CLIENTSHORTNAME": "JAMES WARREN INS SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11208,
    "CLIENTNUMBER": 11208,
    "CLIENTSHORTNAME": "HERBERT WATSON INS BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11209,
    "CLIENTNUMBER": 11209,
    "CLIENTSHORTNAME": "ANDREW WEIR INS BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11210,
    "CLIENTNUMBER": 11210,
    "CLIENTSHORTNAME": "GOODHALE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11211,
    "CLIENTNUMBER": 11211,
    "CLIENTSHORTNAME": "WIGHAM POLAND LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11212,
    "CLIENTNUMBER": 11212,
    "CLIENTSHORTNAME": "WOODS & MASLEN LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11213,
    "CLIENTNUMBER": 11213,
    "CLIENTSHORTNAME": "ALEXANDER & ALEXANDER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11214,
    "CLIENTNUMBER": 11214,
    "CLIENTSHORTNAME": "FE WRIGHT (UK) LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11215,
    "CLIENTNUMBER": 11215,
    "CLIENTSHORTNAME": "WORLDWIDE THOROUGHBRED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11216,
    "CLIENTNUMBER": 11216,
    "CLIENTSHORTNAME": "F CONRADI LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11217,
    "CLIENTNUMBER": 11217,
    "CLIENTSHORTNAME": "MINET PROFESSIONAL SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11218,
    "CLIENTNUMBER": 11218,
    "CLIENTSHORTNAME": "MEACOCK SAMUELSON DEVITT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11219,
    "CLIENTNUMBER": 11219,
    "CLIENTSHORTNAME": "CHILDS REINSURANCE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11220,
    "CLIENTNUMBER": 11220,
    "CLIENTSHORTNAME": "FININS LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11221,
    "CLIENTNUMBER": 11221,
    "CLIENTSHORTNAME": "LONDON GUARANTEE & R/I",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11222,
    "CLIENTNUMBER": 11222,
    "CLIENTSHORTNAME": "CLEMENTSON RODFORD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11223,
    "CLIENTNUMBER": 11223,
    "CLIENTSHORTNAME": "INS BKRS INT'L LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11224,
    "CLIENTNUMBER": 11224,
    "CLIENTSHORTNAME": "FRANK POWER AND CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11225,
    "CLIENTNUMBER": 11225,
    "CLIENTSHORTNAME": "ERNEST A NOTCUTT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11226,
    "CLIENTNUMBER": 11226,
    "CLIENTSHORTNAME": "LONHRO INS BROKERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11227,
    "CLIENTNUMBER": 11227,
    "CLIENTSHORTNAME": "DEWEY WARREN (NASCO)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11228,
    "CLIENTNUMBER": 11228,
    "CLIENTSHORTNAME": "THOMSON HEATH & BOND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11229,
    "CLIENTNUMBER": 11229,
    "CLIENTSHORTNAME": "MACINTYRE KELLETT (R/I)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11230,
    "CLIENTNUMBER": 11230,
    "CLIENTSHORTNAME": "GWB (INS BROKERS) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11231,
    "CLIENTNUMBER": 11231,
    "CLIENTSHORTNAME": "ELIZABETHAN MARINE & GEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11232,
    "CLIENTNUMBER": 11232,
    "CLIENTSHORTNAME": "THOMAS NELSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11233,
    "CLIENTNUMBER": 11233,
    "CLIENTSHORTNAME": "FRANK BRADFORD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11234,
    "CLIENTNUMBER": 11234,
    "CLIENTSHORTNAME": "JAMES KIRKLAND WHITE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11235,
    "CLIENTNUMBER": 11235,
    "CLIENTSHORTNAME": "CORSI AND PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11236,
    "CLIENTNUMBER": 11236,
    "CLIENTSHORTNAME": "CULLIS RAGGETT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11237,
    "CLIENTNUMBER": 11237,
    "CLIENTSHORTNAME": "RONALD OTTEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11238,
    "CLIENTNUMBER": 11238,
    "CLIENTSHORTNAME": "HANNAN & CO (LONDON) LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11239,
    "CLIENTNUMBER": 11239,
    "CLIENTSHORTNAME": "HARRINGTON AUSTIN LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11240,
    "CLIENTNUMBER": 11240,
    "CLIENTSHORTNAME": "BESSO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11241,
    "CLIENTNUMBER": 11241,
    "CLIENTSHORTNAME": "KERSLEY PROCTOR & DAY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11242,
    "CLIENTNUMBER": 11242,
    "CLIENTSHORTNAME": "CAVALIER INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11243,
    "CLIENTNUMBER": 11243,
    "CLIENTSHORTNAME": "BACKHOUSE INT'L INS BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11244,
    "CLIENTNUMBER": 11244,
    "CLIENTSHORTNAME": "MINET PROFESSIONAL(EUROPE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11245,
    "CLIENTNUMBER": 11245,
    "CLIENTSHORTNAME": "BUTCHER ROBINSON STAPLES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11246,
    "CLIENTNUMBER": 11246,
    "CLIENTSHORTNAME": "WILLIS RISK SERVS (IRE)",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11247,
    "CLIENTNUMBER": 11247,
    "CLIENTSHORTNAME": "COYLE HAMILTON (UK) LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11248,
    "CLIENTNUMBER": 11248,
    "CLIENTSHORTNAME": "PETER J CHARMAN & CO LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11249,
    "CLIENTNUMBER": 11249,
    "CLIENTSHORTNAME": "WESTROPP WILSON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11250,
    "CLIENTNUMBER": 11250,
    "CLIENTSHORTNAME": "WILSON ORGANISATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11251,
    "CLIENTNUMBER": 11251,
    "CLIENTSHORTNAME": "BOATWRIGHT CJ & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11252,
    "CLIENTNUMBER": 11252,
    "CLIENTSHORTNAME": "I RYDER-SMITH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11253,
    "CLIENTNUMBER": 11253,
    "CLIENTSHORTNAME": "D R FLEET & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11254,
    "CLIENTNUMBER": 11254,
    "CLIENTSHORTNAME": "HUDIG LANGEVELDT R/I BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11255,
    "CLIENTNUMBER": 11255,
    "CLIENTSHORTNAME": "GALLAGHER LONDON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11256,
    "CLIENTNUMBER": 11256,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11257,
    "CLIENTNUMBER": 11257,
    "CLIENTSHORTNAME": "BRITISH AVIATION INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11258,
    "CLIENTNUMBER": 11258,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11259,
    "CLIENTNUMBER": 11259,
    "CLIENTSHORTNAME": "BLOEMERS & PARTNERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11260,
    "CLIENTNUMBER": 11260,
    "CLIENTSHORTNAME": "GUEST KRIEGER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11261,
    "CLIENTNUMBER": 11261,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11262,
    "CLIENTNUMBER": 11262,
    "CLIENTSHORTNAME": "SABRE INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11263,
    "CLIENTNUMBER": 11263,
    "CLIENTSHORTNAME": "L & O INS BROKERS LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11264,
    "CLIENTNUMBER": 11264,
    "CLIENTSHORTNAME": "HANSON ROBERTS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11265,
    "CLIENTNUMBER": 11265,
    "CLIENTSHORTNAME": "REALE INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11266,
    "CLIENTNUMBER": 11266,
    "CLIENTSHORTNAME": "HOLMES HULBERT & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11267,
    "CLIENTNUMBER": 11267,
    "CLIENTSHORTNAME": "C.HOWARD LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11268,
    "CLIENTNUMBER": 11268,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11269,
    "CLIENTNUMBER": 11269,
    "CLIENTSHORTNAME": "WHITTINGTON GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11270,
    "CLIENTNUMBER": 11270,
    "CLIENTSHORTNAME": "SUMITOMO MARINE & FIRE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11271,
    "CLIENTNUMBER": 11271,
    "CLIENTSHORTNAME": "LLOYD THOMPSON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11272,
    "CLIENTNUMBER": 11272,
    "CLIENTSHORTNAME": "COLLARD & PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11273,
    "CLIENTNUMBER": 11273,
    "CLIENTSHORTNAME": "CGA DIRECT (CTRY GENTS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11274,
    "CLIENTNUMBER": 11274,
    "CLIENTSHORTNAME": "HODGSON MCCREERY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11275,
    "CLIENTNUMBER": 11275,
    "CLIENTSHORTNAME": "GODOLPHIN BLOODSTOCK LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11276,
    "CLIENTNUMBER": 11276,
    "CLIENTSHORTNAME": "ROAD TRANSPORT & GENERAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11277,
    "CLIENTNUMBER": 11277,
    "CLIENTSHORTNAME": "ST KATHERINE'S INS CO.LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11278,
    "CLIENTNUMBER": 11278,
    "CLIENTSHORTNAME": "FENNELL TURNER & TAYLOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11279,
    "CLIENTNUMBER": 11279,
    "CLIENTSHORTNAME": "RANDALL DURKIN & THOMSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11280,
    "CLIENTNUMBER": 11280,
    "CLIENTSHORTNAME": "ALLIED INS BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11281,
    "CLIENTNUMBER": 11281,
    "CLIENTSHORTNAME": "BROOK WHITWELL & NEVILLE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11282,
    "CLIENTNUMBER": 11282,
    "CLIENTSHORTNAME": "CHURCHILL INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11283,
    "CLIENTNUMBER": 11283,
    "CLIENTSHORTNAME": "EURO INS BKRS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11284,
    "CLIENTNUMBER": 11284,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11285,
    "CLIENTNUMBER": 11285,
    "CLIENTSHORTNAME": "TRUMAN PEARSON & BAILEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11286,
    "CLIENTNUMBER": 11286,
    "CLIENTSHORTNAME": "GARRATT SON & FLOWERDEW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11287,
    "CLIENTNUMBER": 11287,
    "CLIENTSHORTNAME": "MACROBIN  LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11288,
    "CLIENTNUMBER": 11288,
    "CLIENTSHORTNAME": "HARGREAVES, REISS & QUINN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11289,
    "CLIENTNUMBER": 11289,
    "CLIENTSHORTNAME": "WOODROFFE STACEY J & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11290,
    "CLIENTNUMBER": 11290,
    "CLIENTSHORTNAME": "TYER LA & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11291,
    "CLIENTNUMBER": 11291,
    "CLIENTSHORTNAME": "MICHAEL HOUSLEY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11292,
    "CLIENTNUMBER": 11292,
    "CLIENTSHORTNAME": "MILES EMBLIN COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11293,
    "CLIENTNUMBER": 11293,
    "CLIENTSHORTNAME": "DAVISON R L & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11294,
    "CLIENTNUMBER": 11294,
    "CLIENTSHORTNAME": "ROWBOTHAM BAXTER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11295,
    "CLIENTNUMBER": 11295,
    "CLIENTSHORTNAME": "SMITH SELMON TEMPLE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11296,
    "CLIENTNUMBER": 11296,
    "CLIENTSHORTNAME": "SYMONS PEMBERTON & SPIERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11297,
    "CLIENTNUMBER": 11297,
    "CLIENTSHORTNAME": "JOHN W DUNHAM & PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11298,
    "CLIENTNUMBER": 11298,
    "CLIENTSHORTNAME": "HILL WG & SON (INS) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11299,
    "CLIENTNUMBER": 11299,
    "CLIENTSHORTNAME": "WATTS WATTS (INSURANCE)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11300,
    "CLIENTNUMBER": 11300,
    "CLIENTSHORTNAME": "ENDSLEIGH  INS SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11301,
    "CLIENTNUMBER": 11301,
    "CLIENTSHORTNAME": "DEVI GRAYS INSURANCE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11302,
    "CLIENTNUMBER": 11302,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11303,
    "CLIENTNUMBER": 11303,
    "CLIENTSHORTNAME": "TRITON INS BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11304,
    "CLIENTNUMBER": 11304,
    "CLIENTSHORTNAME": "RFIB GROUP LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11305,
    "CLIENTNUMBER": 11305,
    "CLIENTSHORTNAME": "CARROLL & CARROLL LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11306,
    "CLIENTNUMBER": 11306,
    "CLIENTSHORTNAME": "EAST ASIATIC BKRS (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11307,
    "CLIENTNUMBER": 11307,
    "CLIENTSHORTNAME": "RICHARDSON HICK & PART",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11308,
    "CLIENTNUMBER": 11308,
    "CLIENTSHORTNAME": "STOVEREED LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11309,
    "CLIENTNUMBER": 11309,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11310,
    "CLIENTNUMBER": 11310,
    "CLIENTSHORTNAME": "COGENT RESOURCES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11311,
    "CLIENTNUMBER": 11311,
    "CLIENTSHORTNAME": "DOUGLAS PHILLIPS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11312,
    "CLIENTNUMBER": 11312,
    "CLIENTSHORTNAME": "BLAKE MARSTON PRIEST",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11313,
    "CLIENTNUMBER": 11313,
    "CLIENTSHORTNAME": "HELMSMAN INS BROKERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11314,
    "CLIENTNUMBER": 11314,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11315,
    "CLIENTNUMBER": 11315,
    "CLIENTSHORTNAME": "SNEATH KENT & STUART LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11316,
    "CLIENTNUMBER": 11316,
    "CLIENTSHORTNAME": "SHERBELL REINSURANCE LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11317,
    "CLIENTNUMBER": 11317,
    "CLIENTSHORTNAME": "ALDGATE GROUP BROKERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11318,
    "CLIENTNUMBER": 11318,
    "CLIENTSHORTNAME": "SPEAR GULLAND & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11319,
    "CLIENTNUMBER": 11319,
    "CLIENTSHORTNAME": "UIC INSURANCE CO. LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11320,
    "CLIENTNUMBER": 11320,
    "CLIENTSHORTNAME": "INBRO CITYGATE INS. BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11321,
    "CLIENTNUMBER": 11321,
    "CLIENTSHORTNAME": "DURHAM DG & CO. LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11322,
    "CLIENTNUMBER": 11322,
    "CLIENTSHORTNAME": "BLANCH CRAWLEY WARREN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11323,
    "CLIENTNUMBER": 11323,
    "CLIENTSHORTNAME": "ROYAL LONDON MUTUAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11324,
    "CLIENTNUMBER": 11324,
    "CLIENTSHORTNAME": "JOHN BANNERMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11325,
    "CLIENTNUMBER": 11325,
    "CLIENTSHORTNAME": "INS CORP CHANNEL ISLAND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11326,
    "CLIENTNUMBER": 11326,
    "CLIENTSHORTNAME": "RICHARD SPARROW & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11327,
    "CLIENTNUMBER": 11327,
    "CLIENTSHORTNAME": "ROBERT BROWNE & PART",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11328,
    "CLIENTNUMBER": 11328,
    "CLIENTSHORTNAME": "HUTCHISON & CRAFT (OSEAS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11329,
    "CLIENTNUMBER": 11329,
    "CLIENTSHORTNAME": "CONGREGATIONAL & GENERAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11330,
    "CLIENTNUMBER": 11330,
    "CLIENTSHORTNAME": "TOWER HILL INS BKRS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11331,
    "CLIENTNUMBER": 11331,
    "CLIENTSHORTNAME": "ROBERT BRUCE FITZMAURICE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11332,
    "CLIENTNUMBER": 11332,
    "CLIENTSHORTNAME": "GROUPAMA GENERAL INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11333,
    "CLIENTNUMBER": 11333,
    "CLIENTSHORTNAME": "LONHAM INT'L BKRS (INS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11334,
    "CLIENTNUMBER": 11334,
    "CLIENTSHORTNAME": "INRE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11335,
    "CLIENTNUMBER": 11335,
    "CLIENTSHORTNAME": "DARWIN CLAYTON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11336,
    "CLIENTNUMBER": 11336,
    "CLIENTSHORTNAME": "COLLIN WILSON BKRS (LDN)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11337,
    "CLIENTNUMBER": 11337,
    "CLIENTSHORTNAME": "GALLAGHER & PLUMER LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11338,
    "CLIENTNUMBER": 11338,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11339,
    "CLIENTNUMBER": 11339,
    "CLIENTSHORTNAME": "MONUMENT INS BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11340,
    "CLIENTNUMBER": 11340,
    "CLIENTSHORTNAME": "SENIOR WRIGHT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11341,
    "CLIENTNUMBER": 11341,
    "CLIENTSHORTNAME": "UNITED INS. BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11342,
    "CLIENTNUMBER": 11342,
    "CLIENTSHORTNAME": "EAGLE STAR (IRELAND)",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11343,
    "CLIENTNUMBER": 11343,
    "CLIENTSHORTNAME": "BIMEH IRAN (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11344,
    "CLIENTNUMBER": 11344,
    "CLIENTSHORTNAME": "BROWN SHIPLEY INS BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11345,
    "CLIENTNUMBER": 11345,
    "CLIENTSHORTNAME": "LEUMI INS. SERVICES (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11346,
    "CLIENTNUMBER": 11346,
    "CLIENTSHORTNAME": "JLT RE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11347,
    "CLIENTNUMBER": 11347,
    "CLIENTSHORTNAME": "MANSON FACILITIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11348,
    "CLIENTNUMBER": 11348,
    "CLIENTSHORTNAME": "WILLCOX JOHNSON & HIGGINS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11349,
    "CLIENTNUMBER": 11349,
    "CLIENTSHORTNAME": "BANNERMAN SLAYDEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11350,
    "CLIENTNUMBER": 11350,
    "CLIENTSHORTNAME": "J TREVOR MORTLEMAN & POL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11351,
    "CLIENTNUMBER": 11351,
    "CLIENTSHORTNAME": "TOWERGATE STAFFORD KNIGHT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11352,
    "CLIENTNUMBER": 11352,
    "CLIENTSHORTNAME": "SBJ NELSON STEAVENSON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11353,
    "CLIENTNUMBER": 11353,
    "CLIENTSHORTNAME": "RUSSELL TUDOR-PRICE & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11354,
    "CLIENTNUMBER": 11354,
    "CLIENTSHORTNAME": "THB GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11355,
    "CLIENTNUMBER": 11355,
    "CLIENTSHORTNAME": "CHARTIS RE SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11356,
    "CLIENTNUMBER": 11356,
    "CLIENTSHORTNAME": "POINTON YORK VOS   (PYV)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11357,
    "CLIENTNUMBER": 11357,
    "CLIENTSHORTNAME": "SYMONS HJ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11358,
    "CLIENTNUMBER": 11358,
    "CLIENTSHORTNAME": "FIELDING MANN (INS BKRS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11359,
    "CLIENTNUMBER": 11359,
    "CLIENTSHORTNAME": "BMS GROUP LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11360,
    "CLIENTNUMBER": 11360,
    "CLIENTSHORTNAME": "CAMERON,RICHARD & SMITH",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11361,
    "CLIENTNUMBER": 11361,
    "CLIENTSHORTNAME": "BERRY PALMER & LYLE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11362,
    "CLIENTNUMBER": 11362,
    "CLIENTSHORTNAME": "HAFNIA INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11363,
    "CLIENTNUMBER": 11363,
    "CLIENTSHORTNAME": "ROGER LARK & SONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11364,
    "CLIENTNUMBER": 11364,
    "CLIENTSHORTNAME": "INSTITUTE LDN UWRS (ILU)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11365,
    "CLIENTNUMBER": 11365,
    "CLIENTSHORTNAME": "WHISTONDALE & PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11366,
    "CLIENTNUMBER": 11366,
    "CLIENTSHORTNAME": "NORMAN REES & PART",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11367,
    "CLIENTNUMBER": 11367,
    "CLIENTSHORTNAME": "PARKER BARNARD R/IE BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11368,
    "CLIENTNUMBER": 11368,
    "CLIENTSHORTNAME": "RISK MANAGEMENT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11369,
    "CLIENTNUMBER": 11369,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11370,
    "CLIENTNUMBER": 11370,
    "CLIENTSHORTNAME": "WIMPEY GROUP SERVICES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11371,
    "CLIENTNUMBER": 11371,
    "CLIENTSHORTNAME": "GLAXO RISK MANAGEMENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11372,
    "CLIENTNUMBER": 11372,
    "CLIENTSHORTNAME": "HARMAN WICKS & SWAYNE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11373,
    "CLIENTNUMBER": 11373,
    "CLIENTSHORTNAME": "BODA JB & CO (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11374,
    "CLIENTNUMBER": 11374,
    "CLIENTSHORTNAME": "SPORTSAFE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11375,
    "CLIENTNUMBER": 11375,
    "CLIENTSHORTNAME": "BANKAMERICA INS BRKS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11376,
    "CLIENTNUMBER": 11376,
    "CLIENTSHORTNAME": "JAMES HUNT DIX (INS) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11377,
    "CLIENTNUMBER": 11377,
    "CLIENTSHORTNAME": "ALWEN HOUGH JOHNSON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11378,
    "CLIENTNUMBER": 11378,
    "CLIENTSHORTNAME": "HOLLAND - 1142",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11379,
    "CLIENTNUMBER": 11379,
    "CLIENTSHORTNAME": "NRI LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11380,
    "CLIENTNUMBER": 11380,
    "CLIENTSHORTNAME": "CAMOMILE UWTG AGENCIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11381,
    "CLIENTNUMBER": 11381,
    "CLIENTSHORTNAME": "GORDON & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11382,
    "CLIENTNUMBER": 11382,
    "CLIENTSHORTNAME": "CORK BAYS AND FISHER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11383,
    "CLIENTNUMBER": 11383,
    "CLIENTSHORTNAME": "JA CHAPMAN & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11384,
    "CLIENTNUMBER": 11384,
    "CLIENTSHORTNAME": "NICHOLSON LESLIE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11385,
    "CLIENTNUMBER": 11385,
    "CLIENTSHORTNAME": "AON CAPITAL MARKETS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11386,
    "CLIENTNUMBER": 11386,
    "CLIENTSHORTNAME": "BOROUGH RUN OFF SER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11387,
    "CLIENTNUMBER": 11387,
    "CLIENTSHORTNAME": "NEEDLER HEATH LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11388,
    "CLIENTNUMBER": 11388,
    "CLIENTSHORTNAME": "COLLECTIVE INS & FIN SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11389,
    "CLIENTNUMBER": 11389,
    "CLIENTSHORTNAME": "D&O MARSHALL'S COUR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11390,
    "CLIENTNUMBER": 11390,
    "CLIENTSHORTNAME": "RUEBEN SEDGWICK INS SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11391,
    "CLIENTNUMBER": 11391,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11392,
    "CLIENTNUMBER": 11392,
    "CLIENTSHORTNAME": "ADAMS ASSOCIATES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11393,
    "CLIENTNUMBER": 11393,
    "CLIENTSHORTNAME": "RICHARDS  LONGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11394,
    "CLIENTNUMBER": 11394,
    "CLIENTSHORTNAME": "CARRITT & PARTNERS LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11395,
    "CLIENTNUMBER": 11395,
    "CLIENTSHORTNAME": "HORACE, HOLMAN & CO. LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11396,
    "CLIENTNUMBER": 11396,
    "CLIENTSHORTNAME": "CLOWES TL & COMPANY LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11397,
    "CLIENTNUMBER": 11397,
    "CLIENTSHORTNAME": "BRYANSTON FINANCIAL & INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11398,
    "CLIENTNUMBER": 11398,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11399,
    "CLIENTNUMBER": 11399,
    "CLIENTSHORTNAME": "NOBLE MARINE (INS BKRS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11400,
    "CLIENTNUMBER": 11400,
    "CLIENTSHORTNAME": "REGIS LOW (EX TYSER LOW)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11401,
    "CLIENTNUMBER": 11401,
    "CLIENTSHORTNAME": "ED BROKING HOLDINGS LLP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11402,
    "CLIENTNUMBER": 11402,
    "CLIENTSHORTNAME": "JAMES HALLAM LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11403,
    "CLIENTNUMBER": 11403,
    "CLIENTSHORTNAME": "GRM INS BKRS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11404,
    "CLIENTNUMBER": 11404,
    "CLIENTSHORTNAME": "GRIFFITHS & ARMOUR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11405,
    "CLIENTNUMBER": 11405,
    "CLIENTSHORTNAME": "DUNCANSON & HOLT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11406,
    "CLIENTNUMBER": 11406,
    "CLIENTSHORTNAME": "ALBERMARLE FPS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11407,
    "CLIENTNUMBER": 11407,
    "CLIENTSHORTNAME": "LINK - 603",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11408,
    "CLIENTNUMBER": 11408,
    "CLIENTSHORTNAME": "BRYANT DEREK INS BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11409,
    "CLIENTNUMBER": 11409,
    "CLIENTSHORTNAME": "HERON PROPERTY CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11410,
    "CLIENTNUMBER": 11410,
    "CLIENTSHORTNAME": "LAMB FS & COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11411,
    "CLIENTNUMBER": 11411,
    "CLIENTSHORTNAME": "WOODGATES INSURANCE BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11412,
    "CLIENTNUMBER": 11412,
    "CLIENTSHORTNAME": "BRITISH EQUESTRIAN BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11413,
    "CLIENTNUMBER": 11413,
    "CLIENTSHORTNAME": "L P H  PITMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11414,
    "CLIENTNUMBER": 11414,
    "CLIENTSHORTNAME": "ABERCROMBIE & KENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11415,
    "CLIENTNUMBER": 11415,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11416,
    "CLIENTNUMBER": 11416,
    "CLIENTSHORTNAME": "ALLIANZ",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11417,
    "CLIENTNUMBER": 11417,
    "CLIENTSHORTNAME": "AON RE UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11418,
    "CLIENTNUMBER": 11418,
    "CLIENTSHORTNAME": "APPLIED TECH ADJUSTING",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11419,
    "CLIENTNUMBER": 11419,
    "CLIENTSHORTNAME": "ARTHUR ANDERSEN & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11420,
    "CLIENTNUMBER": 11420,
    "CLIENTSHORTNAME": "BNP PARIBAS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11421,
    "CLIENTNUMBER": 11421,
    "CLIENTSHORTNAME": "CAPITAL SECURITY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11422,
    "CLIENTNUMBER": 11422,
    "CLIENTSHORTNAME": "CITIBANK PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11423,
    "CLIENTNUMBER": 11423,
    "CLIENTSHORTNAME": "CLYDE & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11424,
    "CLIENTNUMBER": 11424,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11425,
    "CLIENTNUMBER": 11425,
    "CLIENTSHORTNAME": "CONNECTION COURIERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11426,
    "CLIENTNUMBER": 11426,
    "CLIENTSHORTNAME": "GE INSURANCE HOLDINGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11427,
    "CLIENTNUMBER": 11427,
    "CLIENTSHORTNAME": "DAVIES ARNOLD COOPER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11428,
    "CLIENTNUMBER": 11428,
    "CLIENTSHORTNAME": "DENTON HALL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11429,
    "CLIENTNUMBER": 11429,
    "CLIENTSHORTNAME": "HMIT INS DIR (EX DTI)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11430,
    "CLIENTNUMBER": 11430,
    "CLIENTSHORTNAME": "DRAKE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11431,
    "CLIENTNUMBER": 11431,
    "CLIENTSHORTNAME": "STERLING HAMILTON WRIGHT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11432,
    "CLIENTNUMBER": 11432,
    "CLIENTSHORTNAME": "ELLIS & BUCKLE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11433,
    "CLIENTNUMBER": 11433,
    "CLIENTSHORTNAME": "ENGINEERING INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11434,
    "CLIENTNUMBER": 11434,
    "CLIENTSHORTNAME": "ERNST & YOUNG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11435,
    "CLIENTNUMBER": 11435,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11436,
    "CLIENTNUMBER": 11436,
    "CLIENTSHORTNAME": "RE REPORT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11437,
    "CLIENTNUMBER": 11437,
    "CLIENTSHORTNAME": "FINANCIAL DYNAMICS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11438,
    "CLIENTNUMBER": 11438,
    "CLIENTSHORTNAME": "AM BEST WORLDWIDE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": ""
  },
  {
    "id": 11439,
    "CLIENTNUMBER": 11439,
    "CLIENTSHORTNAME": "WORLD LIFE INS. REP.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11440,
    "CLIENTNUMBER": 11440,
    "CLIENTSHORTNAME": "FIRST FIRE & MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11441,
    "CLIENTNUMBER": 11441,
    "CLIENTSHORTNAME": "FRENCH EMBASSY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11442,
    "CLIENTNUMBER": 11442,
    "CLIENTSHORTNAME": "GOLDLINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11443,
    "CLIENTNUMBER": 11443,
    "CLIENTSHORTNAME": "HILL SAMUEL INVESTMENT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11444,
    "CLIENTNUMBER": 11444,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11445,
    "CLIENTNUMBER": 11445,
    "CLIENTSHORTNAME": "ICOS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11446,
    "CLIENTNUMBER": 11446,
    "CLIENTSHORTNAME": "INTERCOM DATA SYSTEMS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11447,
    "CLIENTNUMBER": 11447,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11448,
    "CLIENTNUMBER": 11448,
    "CLIENTSHORTNAME": "KNIGHT FRANK LLP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11449,
    "CLIENTNUMBER": 11449,
    "CLIENTSHORTNAME": "KNOWLES LOSS ADJUSTERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11450,
    "CLIENTNUMBER": 11450,
    "CLIENTSHORTNAME": "LANGITE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11451,
    "CLIENTNUMBER": 11451,
    "CLIENTSHORTNAME": "LAWRENCE GRAHAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11452,
    "CLIENTNUMBER": 11452,
    "CLIENTSHORTNAME": "LAWRENCE INS GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11453,
    "CLIENTNUMBER": 11453,
    "CLIENTSHORTNAME": "EVEREST REINSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11454,
    "CLIENTNUMBER": 11454,
    "CLIENTSHORTNAME": "LINTOTTS SURVEYORS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11455,
    "CLIENTNUMBER": 11455,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11456,
    "CLIENTNUMBER": 11456,
    "CLIENTSHORTNAME": "LUCKY INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11457,
    "CLIENTNUMBER": 11457,
    "CLIENTSHORTNAME": "MAPFRE RE UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11458,
    "CLIENTNUMBER": 11458,
    "CLIENTSHORTNAME": "MCLARENS & TOPLIS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11459,
    "CLIENTNUMBER": 11459,
    "CLIENTSHORTNAME": "MELKI",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11460,
    "CLIENTNUMBER": 11460,
    "CLIENTSHORTNAME": "MITL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11461,
    "CLIENTNUMBER": 11461,
    "CLIENTSHORTNAME": "MOORGATE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11462,
    "CLIENTNUMBER": 11462,
    "CLIENTSHORTNAME": "MARKETING UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11463,
    "CLIENTNUMBER": 11463,
    "CLIENTSHORTNAME": "NEWTON PERKINS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11464,
    "CLIENTNUMBER": 11464,
    "CLIENTSHORTNAME": "NISSAN INS CO EUROPE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11465,
    "CLIENTNUMBER": 11465,
    "CLIENTSHORTNAME": "PAISNER & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11466,
    "CLIENTNUMBER": 11466,
    "CLIENTSHORTNAME": "PANACEA SERVICES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11467,
    "CLIENTNUMBER": 11467,
    "CLIENTSHORTNAME": "PLOUGH COURT FUND MGMT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11468,
    "CLIENTNUMBER": 11468,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11469,
    "CLIENTNUMBER": 11469,
    "CLIENTSHORTNAME": "BAKER & DAVIES REINS SERV",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11470,
    "CLIENTNUMBER": 11470,
    "CLIENTSHORTNAME": "REUTERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11471,
    "CLIENTNUMBER": 11471,
    "CLIENTSHORTNAME": "PLATINUM RE (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11472,
    "CLIENTNUMBER": 11472,
    "CLIENTSHORTNAME": "TONERFLOW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11473,
    "CLIENTNUMBER": 11473,
    "CLIENTSHORTNAME": "TSS ASSET MGT LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11474,
    "CLIENTNUMBER": 11474,
    "CLIENTSHORTNAME": "EASTGATE GROUP LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11475,
    "CLIENTNUMBER": 11475,
    "CLIENTSHORTNAME": "WALTONS & MORSE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11476,
    "CLIENTNUMBER": 11476,
    "CLIENTSHORTNAME": "CATLIN (WELLINGTON) AGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11477,
    "CLIENTNUMBER": 11477,
    "CLIENTSHORTNAME": "WILLIS LTD (LONDON HQ)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11478,
    "CLIENTNUMBER": 11478,
    "CLIENTSHORTNAME": "ZURICH REINS.(LONDON) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11479,
    "CLIENTNUMBER": 11479,
    "CLIENTSHORTNAME": "GHC FIN'L INST INS SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11480,
    "CLIENTNUMBER": 11480,
    "CLIENTSHORTNAME": "J&H MARSH FINPRO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11481,
    "CLIENTNUMBER": 11481,
    "CLIENTSHORTNAME": "EVERSHEDS LLP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11482,
    "CLIENTNUMBER": 11482,
    "CLIENTSHORTNAME": "MARKFIELD (INS BKRS)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11483,
    "CLIENTNUMBER": 11483,
    "CLIENTSHORTNAME": "LOTHBURY INS CO LTD",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11484,
    "CLIENTNUMBER": 11484,
    "CLIENTSHORTNAME": "LDN FIRE&CIVIL DEFENCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11485,
    "CLIENTNUMBER": 11485,
    "CLIENTSHORTNAME": "LONDON INT'L RISK CON",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11486,
    "CLIENTNUMBER": 11486,
    "CLIENTSHORTNAME": "BARCLAYS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11487,
    "CLIENTNUMBER": 11487,
    "CLIENTSHORTNAME": "ALLIANCE & LEICESTER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11488,
    "CLIENTNUMBER": 11488,
    "CLIENTSHORTNAME": "BRADFORD & BINGLEY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11489,
    "CLIENTNUMBER": 11489,
    "CLIENTSHORTNAME": "ABBEY NATIONAL PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11490,
    "CLIENTNUMBER": 11490,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11491,
    "CLIENTNUMBER": 11491,
    "CLIENTSHORTNAME": "NORTHERN ROCK BLDG SOC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11492,
    "CLIENTNUMBER": 11492,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11493,
    "CLIENTNUMBER": 11493,
    "CLIENTSHORTNAME": "POST MAGAZINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11494,
    "CLIENTNUMBER": 11494,
    "CLIENTSHORTNAME": "THE REVIEW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11495,
    "CLIENTNUMBER": 11495,
    "CLIENTSHORTNAME": "REACTIONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11496,
    "CLIENTNUMBER": 11496,
    "CLIENTSHORTNAME": "REINSURANCE MAGAZINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11497,
    "CLIENTNUMBER": 11497,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11498,
    "CLIENTNUMBER": 11498,
    "CLIENTSHORTNAME": "BUSINESS INSURANCE MAG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 3
  },
  {
    "id": 11499,
    "CLIENTNUMBER": 11499,
    "CLIENTSHORTNAME": "BANNISTER DEVELOPMENTS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11500,
    "CLIENTNUMBER": 11500,
    "CLIENTSHORTNAME": "HEWITT BACON & WOODROW",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11501,
    "CLIENTNUMBER": 11501,
    "CLIENTSHORTNAME": "CAMUS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11502,
    "CLIENTNUMBER": 11502,
    "CLIENTSHORTNAME": "CITY UNIVERSITY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11503,
    "CLIENTNUMBER": 11503,
    "CLIENTSHORTNAME": "DOMESTIC & GEN INS (D&G)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11504,
    "CLIENTNUMBER": 11504,
    "CLIENTSHORTNAME": "EAST WEST INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11505,
    "CLIENTNUMBER": 11505,
    "CLIENTSHORTNAME": "HSB ENGINEERING INS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11506,
    "CLIENTNUMBER": 11506,
    "CLIENTSHORTNAME": "KPMG CORPORATE RECOVERY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11507,
    "CLIENTNUMBER": 11507,
    "CLIENTSHORTNAME": "J&H MARSH MARINE & ENERGY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11508,
    "CLIENTNUMBER": 11508,
    "CLIENTSHORTNAME": "HISCOX INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11509,
    "CLIENTNUMBER": 11509,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11510,
    "CLIENTNUMBER": 11510,
    "CLIENTSHORTNAME": "OTHER REINSURERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11511,
    "CLIENTNUMBER": 11511,
    "CLIENTSHORTNAME": "OTH SUBS SCOR GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11512,
    "CLIENTNUMBER": 11512,
    "CLIENTSHORTNAME": "CRAVEN & PARTNERS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11513,
    "CLIENTNUMBER": 11513,
    "CLIENTSHORTNAME": "MANSON JL & PARTNERS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11514,
    "CLIENTNUMBER": 11514,
    "CLIENTSHORTNAME": "SHIRE BROKERS LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11515,
    "CLIENTNUMBER": 11515,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11516,
    "CLIENTNUMBER": 11516,
    "CLIENTSHORTNAME": "BERVALE MEAD INS BRKS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11517,
    "CLIENTNUMBER": 11517,
    "CLIENTSHORTNAME": "JENNER FENTON SLADE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11518,
    "CLIENTNUMBER": 11518,
    "CLIENTSHORTNAME": "ED & F MAN LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11519,
    "CLIENTNUMBER": 11519,
    "CLIENTSHORTNAME": "SEDGWICK ENERGY & MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11520,
    "CLIENTNUMBER": 11520,
    "CLIENTSHORTNAME": "FIRST CITY PARTNERSHIP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11521,
    "CLIENTNUMBER": 11521,
    "CLIENTSHORTNAME": "GLENCAIRN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11522,
    "CLIENTNUMBER": 11522,
    "CLIENTSHORTNAME": "FUJI INT'L INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11523,
    "CLIENTNUMBER": 11523,
    "CLIENTSHORTNAME": "N.I.C.E.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11524,
    "CLIENTNUMBER": 11524,
    "CLIENTSHORTNAME": "MUTUELLE DU MANS ASSUR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11525,
    "CLIENTNUMBER": 11525,
    "CLIENTSHORTNAME": "CENTRAL R/I CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11526,
    "CLIENTNUMBER": 11526,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11527,
    "CLIENTNUMBER": 11527,
    "CLIENTSHORTNAME": "POHJOLA INS CO UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11528,
    "CLIENTNUMBER": 11528,
    "CLIENTSHORTNAME": "PROVIDENCE GRP PRESENCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11529,
    "CLIENTNUMBER": 11529,
    "CLIENTSHORTNAME": "HIBERNIAN INSURANCE PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11530,
    "CLIENTNUMBER": 11530,
    "CLIENTSHORTNAME": "ST PAUL F&M INS CO UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11531,
    "CLIENTNUMBER": 11531,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11532,
    "CLIENTNUMBER": 11532,
    "CLIENTSHORTNAME": "FUJI F&M INS CO UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11533,
    "CLIENTNUMBER": 11533,
    "CLIENTSHORTNAME": "INS CORP OF SINGAPORE UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11534,
    "CLIENTNUMBER": 11534,
    "CLIENTSHORTNAME": "THE NIPPON F&M INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11535,
    "CLIENTNUMBER": 11535,
    "CLIENTSHORTNAME": "JOHN HANCOCK UK INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11536,
    "CLIENTNUMBER": 11536,
    "CLIENTSHORTNAME": "NEW ZEALAND R/I NO.2 A/C",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11537,
    "CLIENTNUMBER": 11537,
    "CLIENTSHORTNAME": "SECURITY INS CO UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11538,
    "CLIENTNUMBER": 11538,
    "CLIENTSHORTNAME": "HARLEYVILLE INS CO UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11539,
    "CLIENTNUMBER": 11539,
    "CLIENTSHORTNAME": "ATLANTIC MUTUAL INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11540,
    "CLIENTNUMBER": 11540,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11541,
    "CLIENTNUMBER": 11541,
    "CLIENTSHORTNAME": "KRAFT INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11542,
    "CLIENTNUMBER": 11542,
    "CLIENTSHORTNAME": "LOUISVILLE INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11543,
    "CLIENTNUMBER": 11543,
    "CLIENTSHORTNAME": "R/I CORP NYK UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11544,
    "CLIENTNUMBER": 11544,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11545,
    "CLIENTNUMBER": 11545,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11546,
    "CLIENTNUMBER": 11546,
    "CLIENTSHORTNAME": "PRESENCE ASSURANCES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11547,
    "CLIENTNUMBER": 11547,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11548,
    "CLIENTNUMBER": 11548,
    "CLIENTSHORTNAME": "ANCIENNE MUTUELLE REINS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11549,
    "CLIENTNUMBER": 11549,
    "CLIENTSHORTNAME": "CHANDOS INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11550,
    "CLIENTNUMBER": 11550,
    "CLIENTSHORTNAME": "SYNDICATE 0587",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11551,
    "CLIENTNUMBER": 11551,
    "CLIENTSHORTNAME": "ABBEY MOTOR - 554",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11552,
    "CLIENTNUMBER": 11552,
    "CLIENTSHORTNAME": "WREN MOTOR POLICIES -1202",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11553,
    "CLIENTNUMBER": 11553,
    "CLIENTSHORTNAME": "BROCKBANK PERSONAL LINES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11554,
    "CLIENTNUMBER": 11554,
    "CLIENTSHORTNAME": "ECLIPSE AT LLOYDS - 913",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11555,
    "CLIENTNUMBER": 11555,
    "CLIENTSHORTNAME": "BROCKBANK - 861",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11556,
    "CLIENTNUMBER": 11556,
    "CLIENTSHORTNAME": "BAKER - 947",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11557,
    "CLIENTNUMBER": 11557,
    "CLIENTSHORTNAME": "TODD - 483",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11558,
    "CLIENTNUMBER": 11558,
    "CLIENTSHORTNAME": "DYER - 484",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11559,
    "CLIENTNUMBER": 11559,
    "CLIENTSHORTNAME": "SMITH - 1113",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11560,
    "CLIENTNUMBER": 11560,
    "CLIENTSHORTNAME": "MANNING - 929",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11561,
    "CLIENTNUMBER": 11561,
    "CLIENTSHORTNAME": "LLOYD - 445",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11562,
    "CLIENTNUMBER": 11562,
    "CLIENTSHORTNAME": "HAUGHTON - 767",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11563,
    "CLIENTNUMBER": 11563,
    "CLIENTSHORTNAME": "SMITH - 257",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11564,
    "CLIENTNUMBER": 11564,
    "CLIENTSHORTNAME": "PRITCHARD SYNDICATE 318",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11565,
    "CLIENTNUMBER": 11565,
    "CLIENTSHORTNAME": "SPYER - 1139",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11566,
    "CLIENTNUMBER": 11566,
    "CLIENTSHORTNAME": "SLADE - 782",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11567,
    "CLIENTNUMBER": 11567,
    "CLIENTSHORTNAME": "CLARE - 10",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11568,
    "CLIENTNUMBER": 11568,
    "CLIENTSHORTNAME": "SYNDICATE 1096",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11569,
    "CLIENTNUMBER": 11569,
    "CLIENTSHORTNAME": "SYNDICATE 1087",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11570,
    "CLIENTNUMBER": 11570,
    "CLIENTSHORTNAME": "JUBILEE SERVICE SOLUTIONS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11571,
    "CLIENTNUMBER": 11571,
    "CLIENTSHORTNAME": "MERRETT - ?",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11572,
    "CLIENTNUMBER": 11572,
    "CLIENTSHORTNAME": "SYNDICATE 1007",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11573,
    "CLIENTNUMBER": 11573,
    "CLIENTSHORTNAME": "FAULKNER - 636",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11574,
    "CLIENTNUMBER": 11574,
    "CLIENTSHORTNAME": "WILLIAMS - 440",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11575,
    "CLIENTNUMBER": 11575,
    "CLIENTSHORTNAME": "DENIS - 1121",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11576,
    "CLIENTNUMBER": 11576,
    "CLIENTSHORTNAME": "MOSS - 785",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11577,
    "CLIENTNUMBER": 11577,
    "CLIENTSHORTNAME": "MURRELL - 1102",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11578,
    "CLIENTNUMBER": 11578,
    "CLIENTSHORTNAME": "MILFORD-COTTAM - 1178",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11579,
    "CLIENTNUMBER": 11579,
    "CLIENTSHORTNAME": "MARKS - 1006",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11580,
    "CLIENTNUMBER": 11580,
    "CLIENTSHORTNAME": "COLTON - 375",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11581,
    "CLIENTNUMBER": 11581,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11582,
    "CLIENTNUMBER": 11582,
    "CLIENTSHORTNAME": "MURPHY - 1156",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11583,
    "CLIENTNUMBER": 11583,
    "CLIENTSHORTNAME": "METCALF - 588",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11584,
    "CLIENTNUMBER": 11584,
    "CLIENTSHORTNAME": "ETHERIDGE - 529",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11585,
    "CLIENTNUMBER": 11585,
    "CLIENTSHORTNAME": "SEAR - 296",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11586,
    "CLIENTNUMBER": 11586,
    "CLIENTSHORTNAME": "DENDY - 546",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11587,
    "CLIENTNUMBER": 11587,
    "CLIENTSHORTNAME": "ADVENT SYNDICATE 780",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11588,
    "CLIENTNUMBER": 11588,
    "CLIENTSHORTNAME": "DARLING - 1162",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11589,
    "CLIENTNUMBER": 11589,
    "CLIENTSHORTNAME": "GAGE - 1152",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11590,
    "CLIENTNUMBER": 11590,
    "CLIENTSHORTNAME": "EDWARDS - 219",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11591,
    "CLIENTNUMBER": 11591,
    "CLIENTSHORTNAME": "SYNDICATE 0657",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11592,
    "CLIENTNUMBER": 11592,
    "CLIENTSHORTNAME": "BILLYARD - 398",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11593,
    "CLIENTNUMBER": 11593,
    "CLIENTSHORTNAME": "TOOMEY - 640",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11594,
    "CLIENTNUMBER": 11594,
    "CLIENTSHORTNAME": "NEIL DR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11595,
    "CLIENTNUMBER": 11595,
    "CLIENTSHORTNAME": "CHESTER - 65",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11596,
    "CLIENTNUMBER": 11596,
    "CLIENTSHORTNAME": "SOMPOCANOPIUS SYND 958",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11597,
    "CLIENTNUMBER": 11597,
    "CLIENTSHORTNAME": "DODSON - 660",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11598,
    "CLIENTNUMBER": 11598,
    "CLIENTSHORTNAME": "BRIEN - 15",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11599,
    "CLIENTNUMBER": 11599,
    "CLIENTSHORTNAME": "GOFTON-SALMOND -  847",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11600,
    "CLIENTNUMBER": 11600,
    "CLIENTSHORTNAME": "HOWEL - 263",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11601,
    "CLIENTNUMBER": 11601,
    "CLIENTSHORTNAME": "DENHAM - 990",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11602,
    "CLIENTNUMBER": 11602,
    "CLIENTSHORTNAME": "MUSGRAVE - 1118",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11603,
    "CLIENTNUMBER": 11603,
    "CLIENTSHORTNAME": "SHARMAN DS ESQ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11604,
    "CLIENTNUMBER": 11604,
    "CLIENTSHORTNAME": "MINTER - 478",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11605,
    "CLIENTNUMBER": 11605,
    "CLIENTSHORTNAME": "JUDD EE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11606,
    "CLIENTNUMBER": 11606,
    "CLIENTSHORTNAME": "LEE AG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11607,
    "CLIENTNUMBER": 11607,
    "CLIENTSHORTNAME": "RING - 272",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11608,
    "CLIENTNUMBER": 11608,
    "CLIENTSHORTNAME": "WEST D ESQ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11609,
    "CLIENTNUMBER": 11609,
    "CLIENTSHORTNAME": "SYNDICATE 1003",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11610,
    "CLIENTNUMBER": 11610,
    "CLIENTSHORTNAME": "MACKINNON - 927",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11611,
    "CLIENTNUMBER": 11611,
    "CLIENTSHORTNAME": "CHRISTMAS - 16",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11612,
    "CLIENTNUMBER": 11612,
    "CLIENTSHORTNAME": "SIRETT - 1011",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11613,
    "CLIENTNUMBER": 11613,
    "CLIENTSHORTNAME": "TROTT - 1091",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11614,
    "CLIENTNUMBER": 11614,
    "CLIENTSHORTNAME": "KEMP TJ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11615,
    "CLIENTNUMBER": 11615,
    "CLIENTSHORTNAME": "TANKARD - 1088",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11616,
    "CLIENTNUMBER": 11616,
    "CLIENTSHORTNAME": "WHITE - 28/29",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11617,
    "CLIENTNUMBER": 11617,
    "CLIENTSHORTNAME": "BARDER & MARSH - 632",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11618,
    "CLIENTNUMBER": 11618,
    "CLIENTSHORTNAME": "HAYDAY - 694",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11619,
    "CLIENTNUMBER": 11619,
    "CLIENTSHORTNAME": "FARADAY SYNDICATE 435",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11620,
    "CLIENTNUMBER": 11620,
    "CLIENTSHORTNAME": "HOLMES - 724",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11621,
    "CLIENTNUMBER": 11621,
    "CLIENTSHORTNAME": "BURNHOPE - 1067",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11622,
    "CLIENTNUMBER": 11622,
    "CLIENTSHORTNAME": "AGNEW - 673",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11623,
    "CLIENTNUMBER": 11623,
    "CLIENTSHORTNAME": "HOLT - 40",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11624,
    "CLIENTNUMBER": 11624,
    "CLIENTSHORTNAME": "O'FARRELL - 1036",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11625,
    "CLIENTNUMBER": 11625,
    "CLIENTSHORTNAME": "WHITE - 31",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11626,
    "CLIENTNUMBER": 11626,
    "CLIENTSHORTNAME": "SYNDICATE 0282",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11627,
    "CLIENTNUMBER": 11627,
    "CLIENTSHORTNAME": "CLEVERLEY - 787",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11628,
    "CLIENTNUMBER": 11628,
    "CLIENTSHORTNAME": "WILKES - 740",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11629,
    "CLIENTNUMBER": 11629,
    "CLIENTSHORTNAME": "THOMPSON - 80",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11630,
    "CLIENTNUMBER": 11630,
    "CLIENTSHORTNAME": "SYNDICATE 0744",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11631,
    "CLIENTNUMBER": 11631,
    "CLIENTSHORTNAME": "STREET - 123",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11632,
    "CLIENTNUMBER": 11632,
    "CLIENTSHORTNAME": "BROMLEY - 475",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11633,
    "CLIENTNUMBER": 11633,
    "CLIENTSHORTNAME": "ADAMS - 209",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11634,
    "CLIENTNUMBER": 11634,
    "CLIENTSHORTNAME": "NEWSON - 396",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11635,
    "CLIENTNUMBER": 11635,
    "CLIENTSHORTNAME": "PLANT - 506",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11636,
    "CLIENTNUMBER": 11636,
    "CLIENTSHORTNAME": "GASCOINE - 901",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11637,
    "CLIENTNUMBER": 11637,
    "CLIENTSHORTNAME": "HUTTON - 803",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11638,
    "CLIENTNUMBER": 11638,
    "CLIENTSHORTNAME": "HILL - 372",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11639,
    "CLIENTNUMBER": 11639,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11640,
    "CLIENTNUMBER": 11640,
    "CLIENTSHORTNAME": "JONES -  329",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11641,
    "CLIENTNUMBER": 11641,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11642,
    "CLIENTNUMBER": 11642,
    "CLIENTSHORTNAME": "JACKSON - 663",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11643,
    "CLIENTNUMBER": 11643,
    "CLIENTSHORTNAME": "SYNDICATE 0062",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11644,
    "CLIENTNUMBER": 11644,
    "CLIENTSHORTNAME": "PATEMAN - 1014",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11645,
    "CLIENTNUMBER": 11645,
    "CLIENTSHORTNAME": "LAWRENCE - 1068",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11646,
    "CLIENTNUMBER": 11646,
    "CLIENTSHORTNAME": "BIRD - 103",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11647,
    "CLIENTNUMBER": 11647,
    "CLIENTSHORTNAME": "THOMPSON - 1049",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11648,
    "CLIENTNUMBER": 11648,
    "CLIENTSHORTNAME": "STRATTON - 782",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11649,
    "CLIENTNUMBER": 11649,
    "CLIENTSHORTNAME": "BOHLING - 834",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11650,
    "CLIENTNUMBER": 11650,
    "CLIENTSHORTNAME": "THEAKSTON - 1101",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11651,
    "CLIENTNUMBER": 11651,
    "CLIENTSHORTNAME": "TOWERS - 679",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11652,
    "CLIENTNUMBER": 11652,
    "CLIENTSHORTNAME": "CRAIG - 411",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11653,
    "CLIENTNUMBER": 11653,
    "CLIENTSHORTNAME": "BARTELL - 1117",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11654,
    "CLIENTNUMBER": 11654,
    "CLIENTSHORTNAME": "GROVE - 561",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11655,
    "CLIENTNUMBER": 11655,
    "CLIENTSHORTNAME": "ENGLISH - 1048",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11656,
    "CLIENTNUMBER": 11656,
    "CLIENTSHORTNAME": "TILLING - 340",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11657,
    "CLIENTNUMBER": 11657,
    "CLIENTSHORTNAME": "PIERI - 872",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11658,
    "CLIENTNUMBER": 11658,
    "CLIENTSHORTNAME": "SYNDICATE 0102",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11659,
    "CLIENTNUMBER": 11659,
    "CLIENTSHORTNAME": "THOMAS - 1034",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11660,
    "CLIENTNUMBER": 11660,
    "CLIENTSHORTNAME": "STONE - 250",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11661,
    "CLIENTNUMBER": 11661,
    "CLIENTSHORTNAME": "BEAZLEY SYNDICATE 623",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11662,
    "CLIENTNUMBER": 11662,
    "CLIENTSHORTNAME": "CHARMAN - 488",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11663,
    "CLIENTNUMBER": 11663,
    "CLIENTSHORTNAME": "OWEN - 718",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11664,
    "CLIENTNUMBER": 11664,
    "CLIENTSHORTNAME": "SYNDICATE 122",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11665,
    "CLIENTNUMBER": 11665,
    "CLIENTSHORTNAME": "ATTENBOROUGH - 144",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11666,
    "CLIENTNUMBER": 11666,
    "CLIENTSHORTNAME": "GRAVETT - 397",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11667,
    "CLIENTNUMBER": 11667,
    "CLIENTSHORTNAME": "INCEPTUM INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11668,
    "CLIENTNUMBER": 11668,
    "CLIENTSHORTNAME": "SECRETAN - 365",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11669,
    "CLIENTNUMBER": 11669,
    "CLIENTSHORTNAME": "SIMNER  - 1104",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11670,
    "CLIENTNUMBER": 11670,
    "CLIENTSHORTNAME": "BARBICAN MAN AGENCY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11671,
    "CLIENTNUMBER": 11671,
    "CLIENTSHORTNAME": "BOVINGTON - 1109",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11672,
    "CLIENTNUMBER": 11672,
    "CLIENTSHORTNAME": "PEPPER - 500",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11673,
    "CLIENTNUMBER": 11673,
    "CLIENTSHORTNAME": "GREEN - 321",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11674,
    "CLIENTNUMBER": 11674,
    "CLIENTSHORTNAME": "HOPE - 1009",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11675,
    "CLIENTNUMBER": 11675,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11676,
    "CLIENTNUMBER": 11676,
    "CLIENTSHORTNAME": "YOUELL - 79",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11677,
    "CLIENTNUMBER": 11677,
    "CLIENTSHORTNAME": "BONNALIE - 490",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11678,
    "CLIENTNUMBER": 11678,
    "CLIENTSHORTNAME": "PETZOLD - 1095",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11679,
    "CLIENTNUMBER": 11679,
    "CLIENTSHORTNAME": "SOLICITORS INDEMNITY MUT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11680,
    "CLIENTNUMBER": 11680,
    "CLIENTSHORTNAME": "WREN INS ASSOCIATION LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11681,
    "CLIENTNUMBER": 11681,
    "CLIENTSHORTNAME": "LARK - 179",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11682,
    "CLIENTNUMBER": 11682,
    "CLIENTSHORTNAME": "ING - 794",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11683,
    "CLIENTNUMBER": 11683,
    "CLIENTSHORTNAME": "ROBERTSON - 322",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11684,
    "CLIENTNUMBER": 11684,
    "CLIENTSHORTNAME": "HOSE - 604",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11685,
    "CLIENTNUMBER": 11685,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11686,
    "CLIENTNUMBER": 11686,
    "CLIENTSHORTNAME": "CRUMP - 957",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11687,
    "CLIENTNUMBER": 11687,
    "CLIENTSHORTNAME": "DWYER - 323",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11688,
    "CLIENTNUMBER": 11688,
    "CLIENTSHORTNAME": "BROAD - 370",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11689,
    "CLIENTNUMBER": 11689,
    "CLIENTSHORTNAME": "BEACHAM - 624",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11690,
    "CLIENTNUMBER": 11690,
    "CLIENTSHORTNAME": "BONDS - 613",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11691,
    "CLIENTNUMBER": 11691,
    "CLIENTSHORTNAME": "TORCH MOTOR - 877",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11692,
    "CLIENTNUMBER": 11692,
    "CLIENTSHORTNAME": "BARRY - 342",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11693,
    "CLIENTNUMBER": 11693,
    "CLIENTSHORTNAME": "BAKER - 923",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11694,
    "CLIENTNUMBER": 11694,
    "CLIENTSHORTNAME": "BAILEY - 896",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11695,
    "CLIENTNUMBER": 11695,
    "CLIENTSHORTNAME": "MAITLAND WW ESQ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11696,
    "CLIENTNUMBER": 11696,
    "CLIENTSHORTNAME": "SYNDICATE 0994",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11697,
    "CLIENTNUMBER": 11697,
    "CLIENTSHORTNAME": "WILLIAMS - 1002",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11698,
    "CLIENTNUMBER": 11698,
    "CLIENTSHORTNAME": "RUDDEN - 45",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11699,
    "CLIENTNUMBER": 11699,
    "CLIENTSHORTNAME": "PILCHER - 365",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11700,
    "CLIENTNUMBER": 11700,
    "CLIENTSHORTNAME": "HANKIN - 584",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11701,
    "CLIENTNUMBER": 11701,
    "CLIENTSHORTNAME": "PAYNE - 305",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11702,
    "CLIENTNUMBER": 11702,
    "CLIENTSHORTNAME": "GOODA - 297",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11703,
    "CLIENTNUMBER": 11703,
    "CLIENTSHORTNAME": "JACKSON - 799",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11704,
    "CLIENTNUMBER": 11704,
    "CLIENTSHORTNAME": "KESTRAL MOTOR POLICIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11705,
    "CLIENTNUMBER": 11705,
    "CLIENTSHORTNAME": "ZENITH - 74",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11706,
    "CLIENTNUMBER": 11706,
    "CLIENTSHORTNAME": "MITRE INSURANCE ASSO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11707,
    "CLIENTNUMBER": 11707,
    "CLIENTSHORTNAME": "NICE - 1019",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11708,
    "CLIENTNUMBER": 11708,
    "CLIENTSHORTNAME": "WRIGHTSON - 90",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11709,
    "CLIENTNUMBER": 11709,
    "CLIENTSHORTNAME": "TAYLOR - 51",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11710,
    "CLIENTNUMBER": 11710,
    "CLIENTSHORTNAME": "HALL & CAULDWELL - 464",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11711,
    "CLIENTNUMBER": 11711,
    "CLIENTSHORTNAME": "EPPS - 454",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11712,
    "CLIENTNUMBER": 11712,
    "CLIENTSHORTNAME": "ERS SYND 218",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11713,
    "CLIENTNUMBER": 11713,
    "CLIENTSHORTNAME": "BRITISH STANDARD ASS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11714,
    "CLIENTNUMBER": 11714,
    "CLIENTSHORTNAME": "CHAPPELL - 156",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11715,
    "CLIENTNUMBER": 11715,
    "CLIENTSHORTNAME": "CLARE - 73",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11716,
    "CLIENTNUMBER": 11716,
    "CLIENTSHORTNAME": "PITT/DAVIES/DOLLING-BAKER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11717,
    "CLIENTNUMBER": 11717,
    "CLIENTSHORTNAME": "DODSON - 660",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11718,
    "CLIENTNUMBER": 11718,
    "CLIENTSHORTNAME": "SYNDICATE 0463",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11719,
    "CLIENTNUMBER": 11719,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11720,
    "CLIENTNUMBER": 11720,
    "CLIENTSHORTNAME": "SYNDICATE 0190",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11721,
    "CLIENTNUMBER": 11721,
    "CLIENTSHORTNAME": "SYNDICATE 0962",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11722,
    "CLIENTNUMBER": 11722,
    "CLIENTSHORTNAME": "HUMM - 625",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11723,
    "CLIENTNUMBER": 11723,
    "CLIENTSHORTNAME": "LOTTER - 919",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11724,
    "CLIENTNUMBER": 11724,
    "CLIENTSHORTNAME": "FLEET - 897",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11725,
    "CLIENTNUMBER": 11725,
    "CLIENTSHORTNAME": "SHIPLEY - 362",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11726,
    "CLIENTNUMBER": 11726,
    "CLIENTSHORTNAME": "SYNDICATE 0839",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11727,
    "CLIENTNUMBER": 11727,
    "CLIENTSHORTNAME": "KILN SYNDICATE 0510",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11728,
    "CLIENTNUMBER": 11728,
    "CLIENTSHORTNAME": "TAYLOR - 527",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11729,
    "CLIENTNUMBER": 11729,
    "CLIENTSHORTNAME": "FIELD - 204",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11730,
    "CLIENTNUMBER": 11730,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11731,
    "CLIENTNUMBER": 11731,
    "CLIENTSHORTNAME": "ASHBY - 750",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11732,
    "CLIENTNUMBER": 11732,
    "CLIENTSHORTNAME": "BROMLEY - 5",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11733,
    "CLIENTNUMBER": 11733,
    "CLIENTSHORTNAME": "BUTCHER - 627",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11734,
    "CLIENTNUMBER": 11734,
    "CLIENTSHORTNAME": "SECRETAN - 366",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11735,
    "CLIENTNUMBER": 11735,
    "CLIENTSHORTNAME": "EVENETT - 658",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11736,
    "CLIENTNUMBER": 11736,
    "CLIENTSHORTNAME": "SYNDICATE 0570",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11737,
    "CLIENTNUMBER": 11737,
    "CLIENTSHORTNAME": "LLOYD - 1234",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11738,
    "CLIENTNUMBER": 11738,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11739,
    "CLIENTNUMBER": 11739,
    "CLIENTSHORTNAME": "SHONE - 735",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11740,
    "CLIENTNUMBER": 11740,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11741,
    "CLIENTNUMBER": 11741,
    "CLIENTSHORTNAME": "PALMER - 920",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11742,
    "CLIENTNUMBER": 11742,
    "CLIENTSHORTNAME": "MERRETT - 418",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11743,
    "CLIENTNUMBER": 11743,
    "CLIENTSHORTNAME": "MILLIGAN - 521",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11744,
    "CLIENTNUMBER": 11744,
    "CLIENTSHORTNAME": "OUTHWAITE - 317",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11745,
    "CLIENTNUMBER": 11745,
    "CLIENTSHORTNAME": "PAYNE - 683",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11746,
    "CLIENTNUMBER": 11746,
    "CLIENTSHORTNAME": "PHILLIPS - ?",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11747,
    "CLIENTNUMBER": 11747,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11748,
    "CLIENTNUMBER": 11748,
    "CLIENTSHORTNAME": "RAVEN - 80",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11749,
    "CLIENTNUMBER": 11749,
    "CLIENTSHORTNAME": "WALKER - ?",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11750,
    "CLIENTNUMBER": 11750,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11751,
    "CLIENTNUMBER": 11751,
    "CLIENTSHORTNAME": "WILLIAMS - 235",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11752,
    "CLIENTNUMBER": 11752,
    "CLIENTSHORTNAME": "HOLDSURE - 892",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11753,
    "CLIENTNUMBER": 11753,
    "CLIENTSHORTNAME": "CAMERON WEBB GILROY SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11754,
    "CLIENTNUMBER": 11754,
    "CLIENTSHORTNAME": "SYNDICATE 0866",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11755,
    "CLIENTNUMBER": 11755,
    "CLIENTSHORTNAME": "MUNICH RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11756,
    "CLIENTNUMBER": 11756,
    "CLIENTSHORTNAME": "BODEN - 710",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11757,
    "CLIENTNUMBER": 11757,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11758,
    "CLIENTNUMBER": 11758,
    "CLIENTSHORTNAME": "MOUNTAIN TR SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11759,
    "CLIENTNUMBER": 11759,
    "CLIENTSHORTNAME": "DAVIES - 174",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11760,
    "CLIENTNUMBER": 11760,
    "CLIENTSHORTNAME": "HISCOX SYNDICATE 33",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11761,
    "CLIENTNUMBER": 11761,
    "CLIENTSHORTNAME": "SYNDICATE 0980",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11762,
    "CLIENTNUMBER": 11762,
    "CLIENTSHORTNAME": "SYNDICATE 1204",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11763,
    "CLIENTNUMBER": 11763,
    "CLIENTSHORTNAME": "COLES - 403",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11764,
    "CLIENTNUMBER": 11764,
    "CLIENTSHORTNAME": "ANCHOR MOTOR AT LLOYDS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11765,
    "CLIENTNUMBER": 11765,
    "CLIENTSHORTNAME": "CROWE MOTOR - 963",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11766,
    "CLIENTNUMBER": 11766,
    "CLIENTSHORTNAME": "KINLOCH MOTOR  AT LLOYDS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11767,
    "CLIENTNUMBER": 11767,
    "CLIENTSHORTNAME": "SYNDICATE 0552",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11768,
    "CLIENTNUMBER": 11768,
    "CLIENTSHORTNAME": "TUDOR MOTOR  AT LLOYDS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11769,
    "CLIENTNUMBER": 11769,
    "CLIENTSHORTNAME": "LLOYD-ROBERTS - 55",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11770,
    "CLIENTNUMBER": 11770,
    "CLIENTSHORTNAME": "BARNES -  225",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11771,
    "CLIENTNUMBER": 11771,
    "CLIENTSHORTNAME": "YELLOP - 551",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11772,
    "CLIENTNUMBER": 11772,
    "CLIENTSHORTNAME": "CORNWELL - 374",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11773,
    "CLIENTNUMBER": 11773,
    "CLIENTSHORTNAME": "JANSON GREEN - 933",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11774,
    "CLIENTNUMBER": 11774,
    "CLIENTSHORTNAME": "GROOM - 455",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11775,
    "CLIENTNUMBER": 11775,
    "CLIENTSHORTNAME": "MARKEL 702",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11776,
    "CLIENTNUMBER": 11776,
    "CLIENTSHORTNAME": "PALMER - 314",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11777,
    "CLIENTNUMBER": 11777,
    "CLIENTSHORTNAME": "PIERI - 309",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11778,
    "CLIENTNUMBER": 11778,
    "CLIENTSHORTNAME": "ARROW MOTOR POLICIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11779,
    "CLIENTNUMBER": 11779,
    "CLIENTSHORTNAME": "PEGASUS - 330",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11780,
    "CLIENTNUMBER": 11780,
    "CLIENTSHORTNAME": "BEACON MOTOR POLICIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11781,
    "CLIENTNUMBER": 11781,
    "CLIENTSHORTNAME": "COUCHER - 689",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11782,
    "CLIENTNUMBER": 11782,
    "CLIENTSHORTNAME": "CHARLESWORTH MOTOR",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11783,
    "CLIENTNUMBER": 11783,
    "CLIENTSHORTNAME": "BAXTER MR SYN 208",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11784,
    "CLIENTNUMBER": 11784,
    "CLIENTSHORTNAME": "ERIN MOTOR AT LLOYDS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11785,
    "CLIENTNUMBER": 11785,
    "CLIENTSHORTNAME": "AXA DIRECT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11786,
    "CLIENTNUMBER": 11786,
    "CLIENTSHORTNAME": "JOHN HOLMAN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11787,
    "CLIENTNUMBER": 11787,
    "CLIENTSHORTNAME": "PHOENIX LIFE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11788,
    "CLIENTNUMBER": 11788,
    "CLIENTSHORTNAME": "JERSEY MUTUAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11789,
    "CLIENTNUMBER": 11789,
    "CLIENTSHORTNAME": "BUILDERS ACCIDENT INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11790,
    "CLIENTNUMBER": 11790,
    "CLIENTSHORTNAME": "LION INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11791,
    "CLIENTNUMBER": 11791,
    "CLIENTSHORTNAME": "WESLEYAN ASSU SOCIETY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11792,
    "CLIENTNUMBER": 11792,
    "CLIENTSHORTNAME": "IDEAL INSURANCE COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11793,
    "CLIENTNUMBER": 11793,
    "CLIENTSHORTNAME": "PARAMOUNT INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11794,
    "CLIENTNUMBER": 11794,
    "CLIENTSHORTNAME": "BUDGET INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11795,
    "CLIENTNUMBER": 11795,
    "CLIENTSHORTNAME": "POOL RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11796,
    "CLIENTNUMBER": 11796,
    "CLIENTSHORTNAME": "PROVIDENT INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11797,
    "CLIENTNUMBER": 11797,
    "CLIENTSHORTNAME": "INSURANCE GB LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11798,
    "CLIENTNUMBER": 11798,
    "CLIENTSHORTNAME": "BANKERS INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11799,
    "CLIENTNUMBER": 11799,
    "CLIENTSHORTNAME": "MARINE OFF OF AMERICA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11800,
    "CLIENTNUMBER": 11800,
    "CLIENTSHORTNAME": "SYNDICATE 0260",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11801,
    "CLIENTNUMBER": 11801,
    "CLIENTSHORTNAME": "HALLMARK INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11802,
    "CLIENTNUMBER": 11802,
    "CLIENTSHORTNAME": "NIB UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11803,
    "CLIENTNUMBER": 11803,
    "CLIENTSHORTNAME": "INT'L INS AND GUARANTEE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11804,
    "CLIENTNUMBER": 11804,
    "CLIENTSHORTNAME": "VIS",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11805,
    "CLIENTNUMBER": 11805,
    "CLIENTSHORTNAME": "ROYAL INS. (GLOBAL)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11806,
    "CLIENTNUMBER": 11806,
    "CLIENTSHORTNAME": "STOP LOSS MUTUAL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11807,
    "CLIENTNUMBER": 11807,
    "CLIENTSHORTNAME": "SUMMIT MOTOR - 887",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11808,
    "CLIENTNUMBER": 11808,
    "CLIENTSHORTNAME": "HGP MOTOR POLICIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11809,
    "CLIENTNUMBER": 11809,
    "CLIENTSHORTNAME": "WHITEMAN DB & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11810,
    "CLIENTNUMBER": 11810,
    "CLIENTSHORTNAME": "WOODGATE & PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11811,
    "CLIENTNUMBER": 11811,
    "CLIENTSHORTNAME": "TRINITY INSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11812,
    "CLIENTNUMBER": 11812,
    "CLIENTSHORTNAME": "RICS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11813,
    "CLIENTNUMBER": 11813,
    "CLIENTSHORTNAME": "NEW INDIA ASSU CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11814,
    "CLIENTNUMBER": 11814,
    "CLIENTSHORTNAME": "LONDON SPECIAL RISKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11815,
    "CLIENTNUMBER": 11815,
    "CLIENTSHORTNAME": "HARTFORD STEAM BOILER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11816,
    "CLIENTNUMBER": 11816,
    "CLIENTSHORTNAME": "AXA INSURANCE UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11817,
    "CLIENTNUMBER": 11817,
    "CLIENTSHORTNAME": "NATURAL CATASTROPHE INS.",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11818,
    "CLIENTNUMBER": 11818,
    "CLIENTSHORTNAME": "WELLINGTON PERSONAL INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11819,
    "CLIENTNUMBER": 11819,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11820,
    "CLIENTNUMBER": 11820,
    "CLIENTSHORTNAME": "DOWA FIRE & MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11821,
    "CLIENTNUMBER": 11821,
    "CLIENTSHORTNAME": "COMITAS SPA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11822,
    "CLIENTNUMBER": 11822,
    "CLIENTSHORTNAME": "SWISS RE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11823,
    "CLIENTNUMBER": 11823,
    "CLIENTSHORTNAME": "SANFRANSISCO R/I CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11824,
    "CLIENTNUMBER": 11824,
    "CLIENTSHORTNAME": "VARIOUS NON-LONDON CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11825,
    "CLIENTNUMBER": 11825,
    "CLIENTSHORTNAME": "YASUDA F&M INS CO EUROPE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11826,
    "CLIENTNUMBER": 11826,
    "CLIENTSHORTNAME": "PGG INS SERVICES LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11827,
    "CLIENTNUMBER": 11827,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11828,
    "CLIENTNUMBER": 11828,
    "CLIENTSHORTNAME": "MUNICH REINSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11829,
    "CLIENTNUMBER": 11829,
    "CLIENTSHORTNAME": "ROYAL INT'L INS. HLDGS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11830,
    "CLIENTNUMBER": 11830,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11831,
    "CLIENTNUMBER": 11831,
    "CLIENTSHORTNAME": "FAI INSURANCES LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11832,
    "CLIENTNUMBER": 11832,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11833,
    "CLIENTNUMBER": 11833,
    "CLIENTSHORTNAME": "PROTEA ASSU CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11834,
    "CLIENTNUMBER": 11834,
    "CLIENTSHORTNAME": "FOGG TRAVEL INS SER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11835,
    "CLIENTNUMBER": 11835,
    "CLIENTSHORTNAME": "ALL AMERICAN MARINE SLIP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11836,
    "CLIENTNUMBER": 11836,
    "CLIENTSHORTNAME": "AMERICAN INDEMNITY CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11837,
    "CLIENTNUMBER": 11837,
    "CLIENTSHORTNAME": "BRITISH & FOREIGN INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11838,
    "CLIENTNUMBER": 11838,
    "CLIENTSHORTNAME": "BESSEMER INSURANCE CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11839,
    "CLIENTNUMBER": 11839,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11840,
    "CLIENTNUMBER": 11840,
    "CLIENTSHORTNAME": "ROYAL R/I CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11841,
    "CLIENTNUMBER": 11841,
    "CLIENTSHORTNAME": "ARCHITECTS & PI AGENCIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11842,
    "CLIENTNUMBER": 11842,
    "CLIENTSHORTNAME": "AMA UWTG AGENCIES LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11843,
    "CLIENTNUMBER": 11843,
    "CLIENTSHORTNAME": "GROUPE EUROPEEN SA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11844,
    "CLIENTNUMBER": 11844,
    "CLIENTSHORTNAME": "MEDICAL PRACTITIONERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11845,
    "CLIENTNUMBER": 11845,
    "CLIENTSHORTNAME": "BALOISE INS COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11846,
    "CLIENTNUMBER": 11846,
    "CLIENTSHORTNAME": "GORDIAN RUNOFF (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11847,
    "CLIENTNUMBER": 11847,
    "CLIENTSHORTNAME": "MIDDLEMISS - 201",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11848,
    "CLIENTNUMBER": 11848,
    "CLIENTSHORTNAME": "AIM BELGIQUE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11849,
    "CLIENTNUMBER": 11849,
    "CLIENTSHORTNAME": "INDUSTRIAL MUTUAL INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11850,
    "CLIENTNUMBER": 11850,
    "CLIENTSHORTNAME": "EMMS - 836",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11851,
    "CLIENTNUMBER": 11851,
    "CLIENTSHORTNAME": "NYK MARINE MANAGERS INC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11852,
    "CLIENTNUMBER": 11852,
    "CLIENTSHORTNAME": "EDWARDS - 207",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11853,
    "CLIENTNUMBER": 11853,
    "CLIENTSHORTNAME": "PAKISTAN INSURANCE CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11854,
    "CLIENTNUMBER": 11854,
    "CLIENTSHORTNAME": "BULGARIAN FOREIGN INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11855,
    "CLIENTNUMBER": 11855,
    "CLIENTSHORTNAME": "ITALIA ASSICURAZIONI SPA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11856,
    "CLIENTNUMBER": 11856,
    "CLIENTSHORTNAME": "VESTA REINSURANCE CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11857,
    "CLIENTNUMBER": 11857,
    "CLIENTSHORTNAME": "AGNEW - 672",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11858,
    "CLIENTNUMBER": 11858,
    "CLIENTSHORTNAME": "MUTUAL MARINE OFFICE INC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11859,
    "CLIENTNUMBER": 11859,
    "CLIENTSHORTNAME": "COTESWORTH - 535",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11860,
    "CLIENTNUMBER": 11860,
    "CLIENTSHORTNAME": "NETWORK RAIL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11861,
    "CLIENTNUMBER": 11861,
    "CLIENTSHORTNAME": "SCOTTISH LION INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11862,
    "CLIENTNUMBER": 11862,
    "CLIENTSHORTNAME": "TAI PING INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11863,
    "CLIENTNUMBER": 11863,
    "CLIENTSHORTNAME": "AMERICAN OFFSHORE SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11864,
    "CLIENTNUMBER": 11864,
    "CLIENTSHORTNAME": "PORTER RAJ",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11865,
    "CLIENTNUMBER": 11865,
    "CLIENTSHORTNAME": "EVANS BL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11866,
    "CLIENTNUMBER": 11866,
    "CLIENTSHORTNAME": "ARCHER AJ SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11867,
    "CLIENTNUMBER": 11867,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11868,
    "CLIENTNUMBER": 11868,
    "CLIENTSHORTNAME": "MARITIME INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11869,
    "CLIENTNUMBER": 11869,
    "CLIENTSHORTNAME": "AIOI - ANDIE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11870,
    "CLIENTNUMBER": 11870,
    "CLIENTSHORTNAME": "CHRISTIANIA GEN INS NYK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11871,
    "CLIENTNUMBER": 11871,
    "CLIENTSHORTNAME": "QBE REINSURANCE (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11872,
    "CLIENTNUMBER": 11872,
    "CLIENTSHORTNAME": "OIL & GAS INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11873,
    "CLIENTNUMBER": 11873,
    "CLIENTSHORTNAME": "AIG OIL RIG DIVISION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11874,
    "CLIENTNUMBER": 11874,
    "CLIENTSHORTNAME": "PHOENIX F&M INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11875,
    "CLIENTNUMBER": 11875,
    "CLIENTSHORTNAME": "AL KHALEEJ INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11876,
    "CLIENTNUMBER": 11876,
    "CLIENTSHORTNAME": "NAT'L HOUSE BLDG (NHBC)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11877,
    "CLIENTNUMBER": 11877,
    "CLIENTSHORTNAME": "SURVEYORS MUT INS ASSO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11878,
    "CLIENTNUMBER": 11878,
    "CLIENTSHORTNAME": "LIBM LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11879,
    "CLIENTNUMBER": 11879,
    "CLIENTSHORTNAME": "PREFERRED ASSU CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11880,
    "CLIENTNUMBER": 11880,
    "CLIENTSHORTNAME": "GHANA R/I ORGANISATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11881,
    "CLIENTNUMBER": 11881,
    "CLIENTSHORTNAME": "WINTERTHUR-SOCIETE SUISSE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11882,
    "CLIENTNUMBER": 11882,
    "CLIENTSHORTNAME": "LIBANO-SUISSE SAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11883,
    "CLIENTNUMBER": 11883,
    "CLIENTSHORTNAME": "PAN ATLANTIC INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11884,
    "CLIENTNUMBER": 11884,
    "CLIENTSHORTNAME": "KEMPER R/I LONDON LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11885,
    "CLIENTNUMBER": 11885,
    "CLIENTSHORTNAME": "RIDGWELL, FOX & PARTNERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11886,
    "CLIENTNUMBER": 11886,
    "CLIENTSHORTNAME": "ECIC UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11887,
    "CLIENTNUMBER": 11887,
    "CLIENTSHORTNAME": "BATTLE - 456",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11888,
    "CLIENTNUMBER": 11888,
    "CLIENTSHORTNAME": "ATLANTICA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11889,
    "CLIENTNUMBER": 11889,
    "CLIENTSHORTNAME": "GRIFFIN INS ASSO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11890,
    "CLIENTNUMBER": 11890,
    "CLIENTSHORTNAME": "BAR INDM MUTUAL INS ASS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11891,
    "CLIENTNUMBER": 11891,
    "CLIENTSHORTNAME": "MUNICIPAL GEN INS (MGI)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11892,
    "CLIENTNUMBER": 11892,
    "CLIENTSHORTNAME": "CATALINA UK (AM R/I UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11893,
    "CLIENTNUMBER": 11893,
    "CLIENTSHORTNAME": "TRANSECURE SA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11894,
    "CLIENTNUMBER": 11894,
    "CLIENTSHORTNAME": "WARTA INS & R/I CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11895,
    "CLIENTNUMBER": 11895,
    "CLIENTSHORTNAME": "KOREA FOREIGN INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11896,
    "CLIENTNUMBER": 11896,
    "CLIENTSHORTNAME": "ATICAM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11897,
    "CLIENTNUMBER": 11897,
    "CLIENTSHORTNAME": "CHEVANSTELL LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11898,
    "CLIENTNUMBER": 11898,
    "CLIENTSHORTNAME": "HUNGARIA INS/R/I & EXPO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11899,
    "CLIENTNUMBER": 11899,
    "CLIENTSHORTNAME": "ST PAUL INT'L INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11900,
    "CLIENTNUMBER": 11900,
    "CLIENTSHORTNAME": "BRITANNIA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11901,
    "CLIENTNUMBER": 11901,
    "CLIENTSHORTNAME": "MUTUAL ACCOUNTANTS PI CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11902,
    "CLIENTNUMBER": 11902,
    "CLIENTSHORTNAME": "INTERCONTINENTAL INS SER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11903,
    "CLIENTNUMBER": 11903,
    "CLIENTSHORTNAME": "SWITZERLAND INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11904,
    "CLIENTNUMBER": 11904,
    "CLIENTSHORTNAME": "DIRECT LINE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11905,
    "CLIENTNUMBER": 11905,
    "CLIENTSHORTNAME": "HIGH STREET INSURANCE CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11906,
    "CLIENTNUMBER": 11906,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11907,
    "CLIENTNUMBER": 11907,
    "CLIENTSHORTNAME": "METHODIST INS PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11908,
    "CLIENTNUMBER": 11908,
    "CLIENTSHORTNAME": "HAGTRYGGING H.F.",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 11909,
    "CLIENTNUMBER": 11909,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11910,
    "CLIENTNUMBER": 11910,
    "CLIENTSHORTNAME": "DELTA INT'L R/I CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11911,
    "CLIENTNUMBER": 11911,
    "CLIENTSHORTNAME": "UNIVERSAL INS CO IRELAND",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11912,
    "CLIENTNUMBER": 11912,
    "CLIENTSHORTNAME": "STERLING INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11913,
    "CLIENTNUMBER": 11913,
    "CLIENTSHORTNAME": "AXA PMPA INSURANCE",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11914,
    "CLIENTNUMBER": 11914,
    "CLIENTSHORTNAME": "FRIENDS FIRST GENERAL",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11915,
    "CLIENTNUMBER": 11915,
    "CLIENTSHORTNAME": "AA MUTUAL INT'L INS.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11916,
    "CLIENTNUMBER": 11916,
    "CLIENTSHORTNAME": "ARMES - 820",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11917,
    "CLIENTNUMBER": 11917,
    "CLIENTSHORTNAME": "CENTRAL OF ICELAND",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 11918,
    "CLIENTNUMBER": 11918,
    "CLIENTSHORTNAME": "VARIOUS LLOYDS & LDN CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11919,
    "CLIENTNUMBER": 11919,
    "CLIENTSHORTNAME": "VARIOUS LONDON COMPANIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11920,
    "CLIENTNUMBER": 11920,
    "CLIENTSHORTNAME": "UNITED ASSURANCE GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11921,
    "CLIENTNUMBER": 11921,
    "CLIENTSHORTNAME": "LDN MTR CAB PROPRIETORS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11922,
    "CLIENTNUMBER": 11922,
    "CLIENTSHORTNAME": "FM INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11923,
    "CLIENTNUMBER": 11923,
    "CLIENTSHORTNAME": "BRITISH NATIONAL LIFE INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11924,
    "CLIENTNUMBER": 11924,
    "CLIENTSHORTNAME": "SUNDERLAND MARINE MUT INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11925,
    "CLIENTNUMBER": 11925,
    "CLIENTSHORTNAME": "ISLENSK ENDURTRYGGING",
    "HEADOFFICECITY": "ISL",
    "CLIENTSUBSIDIARYCODE": 5
  },
  {
    "id": 11926,
    "CLIENTNUMBER": 11926,
    "CLIENTSHORTNAME": "NORTHERN STAR INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11927,
    "CLIENTNUMBER": 11927,
    "CLIENTSHORTNAME": "GE SPECIALTY INS (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11928,
    "CLIENTNUMBER": 11928,
    "CLIENTSHORTNAME": "NORWICH WINTERHUR AUSTRAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11929,
    "CLIENTNUMBER": 11929,
    "CLIENTSHORTNAME": "BRIMCO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11930,
    "CLIENTNUMBER": 11930,
    "CLIENTSHORTNAME": "MUNICIPAL MUTUAL (MMI)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11931,
    "CLIENTNUMBER": 11931,
    "CLIENTSHORTNAME": "SKANDIA UK INSURANCE PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11932,
    "CLIENTNUMBER": 11932,
    "CLIENTSHORTNAME": "IRON TRADES INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11933,
    "CLIENTNUMBER": 11933,
    "CLIENTSHORTNAME": "STAR COVER",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11934,
    "CLIENTNUMBER": 11934,
    "CLIENTSHORTNAME": "OCEANUS MUTUAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11935,
    "CLIENTNUMBER": 11935,
    "CLIENTSHORTNAME": "AGEAS INS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11936,
    "CLIENTNUMBER": 11936,
    "CLIENTSHORTNAME": "ZURICH INSURANCE CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11937,
    "CLIENTNUMBER": 11937,
    "CLIENTSHORTNAME": "STIRLING COOKE INS. BKRS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11938,
    "CLIENTNUMBER": 11938,
    "CLIENTSHORTNAME": "PHOENIX ASSU COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11939,
    "CLIENTNUMBER": 11939,
    "CLIENTSHORTNAME": "GUY CARPENTER & CO(GBR)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11940,
    "CLIENTNUMBER": 11940,
    "CLIENTSHORTNAME": "MUTUAL OF OMAHA INS (LDN)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11941,
    "CLIENTNUMBER": 11941,
    "CLIENTSHORTNAME": "LEXINGTON INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11942,
    "CLIENTNUMBER": 11942,
    "CLIENTSHORTNAME": "AFIA-HOME INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11943,
    "CLIENTNUMBER": 11943,
    "CLIENTSHORTNAME": "NATIONAL VULCAN ENG",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11944,
    "CLIENTNUMBER": 11944,
    "CLIENTSHORTNAME": "AXA INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11945,
    "CLIENTNUMBER": 11945,
    "CLIENTSHORTNAME": "CENTURY INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11946,
    "CLIENTNUMBER": 11946,
    "CLIENTSHORTNAME": "MARINER UWTG AGENCIES",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11947,
    "CLIENTNUMBER": 11947,
    "CLIENTSHORTNAME": "SOCIETE NAT'L D'ASS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11948,
    "CLIENTNUMBER": 11948,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11949,
    "CLIENTNUMBER": 11949,
    "CLIENTSHORTNAME": "GM IMBER LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11950,
    "CLIENTNUMBER": 11950,
    "CLIENTSHORTNAME": "ASIAN REINSURANCE CORP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11951,
    "CLIENTNUMBER": 11951,
    "CLIENTSHORTNAME": "SINGAPORE R/I CORPORATION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11952,
    "CLIENTNUMBER": 11952,
    "CLIENTSHORTNAME": "ALEXANDER HOWDEN GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11953,
    "CLIENTNUMBER": 11953,
    "CLIENTSHORTNAME": "SPHERE DRAKE INS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11954,
    "CLIENTNUMBER": 11954,
    "CLIENTSHORTNAME": "SOVEREIGN MARINE & GEN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11955,
    "CLIENTNUMBER": 11955,
    "CLIENTSHORTNAME": "UK INSURANCE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11956,
    "CLIENTNUMBER": 11956,
    "CLIENTSHORTNAME": "ROYAL INS (UK) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11957,
    "CLIENTNUMBER": 11957,
    "CLIENTSHORTNAME": "RIVER THAMES INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11958,
    "CLIENTNUMBER": 11958,
    "CLIENTSHORTNAME": "PEARL ASSURANCE PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11959,
    "CLIENTNUMBER": 11959,
    "CLIENTSHORTNAME": "OCEAN MARINE INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11960,
    "CLIENTNUMBER": 11960,
    "CLIENTSHORTNAME": "WINTERTHUR INT'L INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11961,
    "CLIENTNUMBER": 11961,
    "CLIENTSHORTNAME": "AVIVA INS UK LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11962,
    "CLIENTNUMBER": 11962,
    "CLIENTSHORTNAME": "COVEA INS PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11963,
    "CLIENTNUMBER": 11963,
    "CLIENTSHORTNAME": "NFU MUTUAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11964,
    "CLIENTNUMBER": 11964,
    "CLIENTSHORTNAME": "NATIONAL EMPLOYERS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11965,
    "CLIENTNUMBER": 11965,
    "CLIENTSHORTNAME": "GROUPAMA INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11966,
    "CLIENTNUMBER": 11966,
    "CLIENTSHORTNAME": "MERCANTILE & GEN (M&G)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11967,
    "CLIENTNUMBER": 11967,
    "CLIENTSHORTNAME": "MALVERN INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11968,
    "CLIENTNUMBER": 11968,
    "CLIENTSHORTNAME": "AXA GLOBAL RISKS (UK)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11969,
    "CLIENTNUMBER": 11969,
    "CLIENTSHORTNAME": "LONDON/SUN ASSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11970,
    "CLIENTNUMBER": 11970,
    "CLIENTSHORTNAME": "GENERAL R/I LTD.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11971,
    "CLIENTNUMBER": 11971,
    "CLIENTSHORTNAME": "CIGNA",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11972,
    "CLIENTNUMBER": 11972,
    "CLIENTSHORTNAME": "IRISH NATIONAL",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11973,
    "CLIENTNUMBER": 11973,
    "CLIENTSHORTNAME": "INDEMNITY MARINE ASS. CO.",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11974,
    "CLIENTNUMBER": 11974,
    "CLIENTSHORTNAME": "OROC (1998) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11975,
    "CLIENTNUMBER": 11975,
    "CLIENTSHORTNAME": "IC INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11976,
    "CLIENTNUMBER": 11976,
    "CLIENTSHORTNAME": "HIBERNIAN AVIVA GROUP",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11977,
    "CLIENTNUMBER": 11977,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11978,
    "CLIENTNUMBER": 11978,
    "CLIENTSHORTNAME": "LLOYD'S TSB GEN INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11979,
    "CLIENTNUMBER": 11979,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11980,
    "CLIENTNUMBER": 11980,
    "CLIENTSHORTNAME": "FOLGATE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11981,
    "CLIENTNUMBER": 11981,
    "CLIENTSHORTNAME": "WARRINGTON ME & OTHS S106",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11982,
    "CLIENTNUMBER": 11982,
    "CLIENTSHORTNAME": "FBD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11983,
    "CLIENTNUMBER": 11983,
    "CLIENTSHORTNAME": "HISCOX INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11984,
    "CLIENTNUMBER": 11984,
    "CLIENTSHORTNAME": "ECCLESIASTICAL INS OFFICE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11985,
    "CLIENTNUMBER": 11985,
    "CLIENTSHORTNAME": "EAGLE STAR INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11986,
    "CLIENTNUMBER": 11986,
    "CLIENTSHORTNAME": "CO-OPERATIVE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11987,
    "CLIENTNUMBER": 11987,
    "CLIENTSHORTNAME": "CONTINENTAL ASSU LDN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11988,
    "CLIENTNUMBER": 11988,
    "CLIENTSHORTNAME": "ALLIANZ IRELAND PLC",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11989,
    "CLIENTNUMBER": 11989,
    "CLIENTSHORTNAME": "BRITISH RESERVE INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11990,
    "CLIENTNUMBER": 11990,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11991,
    "CLIENTNUMBER": 11991,
    "CLIENTSHORTNAME": "BLACK SEA &BALTIC GEN INS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11992,
    "CLIENTNUMBER": 11992,
    "CLIENTSHORTNAME": "LEADENHALL/BISHOPSGATE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11993,
    "CLIENTNUMBER": 11993,
    "CLIENTSHORTNAME": "AVON INSURANCE PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11994,
    "CLIENTNUMBER": 11994,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11995,
    "CLIENTNUMBER": 11995,
    "CLIENTSHORTNAME": "SUN ALLIANCE INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11996,
    "CLIENTNUMBER": 11996,
    "CLIENTSHORTNAME": "HAMBURGER PHOENIX INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 11997,
    "CLIENTNUMBER": 11997,
    "CLIENTSHORTNAME": "TOKIO M&F INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11998,
    "CLIENTNUMBER": 11998,
    "CLIENTSHORTNAME": "MITSUI SUMITOMO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 11999,
    "CLIENTNUMBER": 11999,
    "CLIENTSHORTNAME": "BRUCE GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12000,
    "CLIENTNUMBER": 12000,
    "CLIENTSHORTNAME": "BRISTOL REINSURANCE LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12001,
    "CLIENTNUMBER": 12001,
    "CLIENTSHORTNAME": "NORTHERN MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12002,
    "CLIENTNUMBER": 12002,
    "CLIENTSHORTNAME": "AMBASSADOR MOTORS - 1144",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12003,
    "CLIENTNUMBER": 12003,
    "CLIENTSHORTNAME": "OIL DISTRIBUTORS INS",
    "HEADOFFICECITY": "GGY",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12004,
    "CLIENTNUMBER": 12004,
    "CLIENTSHORTNAME": "PRIVILEGE INSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12005,
    "CLIENTNUMBER": 12005,
    "CLIENTSHORTNAME": "UIA (INSURANCE)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12006,
    "CLIENTNUMBER": 12006,
    "CLIENTSHORTNAME": "ALS - 293",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12007,
    "CLIENTNUMBER": 12007,
    "CLIENTSHORTNAME": "BURKE FORD INS BRKS",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12008,
    "CLIENTNUMBER": 12008,
    "CLIENTSHORTNAME": "GRIFFIN INS COMPANY LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12009,
    "CLIENTNUMBER": 12009,
    "CLIENTSHORTNAME": "ATTWOOD & HART LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12010,
    "CLIENTNUMBER": 12010,
    "CLIENTSHORTNAME": "AON RISK SERVICES UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12011,
    "CLIENTNUMBER": 12011,
    "CLIENTSHORTNAME": "CHRISTCHURCH INS BKRS LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12012,
    "CLIENTNUMBER": 12012,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12013,
    "CLIENTNUMBER": 12013,
    "CLIENTSHORTNAME": "TRANSGUARD INS COMPANY",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12014,
    "CLIENTNUMBER": 12014,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12015,
    "CLIENTNUMBER": 12015,
    "CLIENTSHORTNAME": "TRB (LONDON) LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12016,
    "CLIENTNUMBER": 12016,
    "CLIENTSHORTNAME": "KWELM",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12017,
    "CLIENTNUMBER": 12017,
    "CLIENTSHORTNAME": "BRITISH GAS INS CO",
    "HEADOFFICECITY": "IMN",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12018,
    "CLIENTNUMBER": 12018,
    "CLIENTSHORTNAME": "PAUL GROUP INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12019,
    "CLIENTNUMBER": 12019,
    "CLIENTSHORTNAME": "COLEMAN CJ & CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12020,
    "CLIENTNUMBER": 12020,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12021,
    "CLIENTNUMBER": 12021,
    "CLIENTSHORTNAME": "SENATOR GROUP",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12022,
    "CLIENTNUMBER": 12022,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12023,
    "CLIENTNUMBER": 12023,
    "CLIENTSHORTNAME": "HIBERNIAN AVIVA",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12024,
    "CLIENTNUMBER": 12024,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12025,
    "CLIENTNUMBER": 12025,
    "CLIENTSHORTNAME": "GUILDHALL INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12026,
    "CLIENTNUMBER": 12026,
    "CLIENTSHORTNAME": "COMMERCIAL UNION",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12027,
    "CLIENTNUMBER": 12027,
    "CLIENTSHORTNAME": "DOWNLANDS LIAB MNGMNT",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12028,
    "CLIENTNUMBER": 12028,
    "CLIENTSHORTNAME": "PRUDENTIAL INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12029,
    "CLIENTNUMBER": 12029,
    "CLIENTSHORTNAME": "BVF (U/WING MNGMNT) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12030,
    "CLIENTNUMBER": 12030,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12031,
    "CLIENTNUMBER": 12031,
    "CLIENTSHORTNAME": "AEGON UK",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12032,
    "CLIENTNUMBER": 12032,
    "CLIENTSHORTNAME": "ATLAS ASSURANCE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12033,
    "CLIENTNUMBER": 12033,
    "CLIENTSHORTNAME": "UK P&I CLUB MILLER P&I",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12034,
    "CLIENTNUMBER": 12034,
    "CLIENTSHORTNAME": "HEATH AVIATION MARINE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12035,
    "CLIENTNUMBER": 12035,
    "CLIENTSHORTNAME": "QBE R/I (EUROPE) LTD",
    "HEADOFFICECITY": "IRL",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12036,
    "CLIENTNUMBER": 12036,
    "CLIENTSHORTNAME": "QBE INS (EUROPE) LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12037,
    "CLIENTNUMBER": 12037,
    "CLIENTSHORTNAME": "SIRIUS (UK) INSURANCE PLC",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12038,
    "CLIENTNUMBER": 12038,
    "CLIENTSHORTNAME": "ZURICH MUNICIPAL",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12039,
    "CLIENTNUMBER": 12039,
    "CLIENTSHORTNAME": "SCOTTISH EAGLE INS CO LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12040,
    "CLIENTNUMBER": 12040,
    "CLIENTSHORTNAME": "SCOR UK COMPANY LIMITED",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12041,
    "CLIENTNUMBER": 12041,
    "CLIENTSHORTNAME": "LOWNDES LAMBERT INT'L",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12042,
    "CLIENTNUMBER": 12042,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12043,
    "CLIENTNUMBER": 12043,
    "CLIENTSHORTNAME": "HEATH INS BROKING LTD",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12044,
    "CLIENTNUMBER": 12044,
    "CLIENTSHORTNAME": "ALSTON GAYLER & CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12045,
    "CLIENTNUMBER": 12045,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12046,
    "CLIENTNUMBER": 12046,
    "CLIENTSHORTNAME": "KININMONTH LAMBT (CANADA)",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12047,
    "CLIENTNUMBER": 12047,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12048,
    "CLIENTNUMBER": 12048,
    "CLIENTSHORTNAME": "BRITISH ECONOMIC INS CO",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12049,
    "CLIENTNUMBER": 12049,
    "CLIENTSHORTNAME": "CHILD CW SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12050,
    "CLIENTNUMBER": 12050,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12051,
    "CLIENTNUMBER": 12051,
    "CLIENTSHORTNAME": "WILLS DR SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12052,
    "CLIENTNUMBER": 12052,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12053,
    "CLIENTNUMBER": 12053,
    "CLIENTSHORTNAME": "DO NOT USE",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12054,
    "CLIENTNUMBER": 12054,
    "CLIENTSHORTNAME": "ALDER - 122",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  },
  {
    "id": 12055,
    "CLIENTNUMBER": 12055,
    "CLIENTSHORTNAME": "BARRETT KR SYN",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 2
  },
  {
    "id": 12056,
    "CLIENTNUMBER": 12056,
    "CLIENTSHORTNAME": "LLOYD TOMS SYND",
    "HEADOFFICECITY": "GBR",
    "CLIENTSUBSIDIARYCODE": 1
  }
];

const coutryAlt = [
  {
    "id": "ABW",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ABW",
    "COUNTRYOFFICIALNAME": "ARUBA",
    "COUNTRYSHORTNAME": "ARUBA",
    "CURRENCYCODE": "AWG",
    "TELEPHONECODE": 297,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AFG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AFG",
    "COUNTRYOFFICIALNAME": "ISLAMIC STATE OF AFGHANISTAN",
    "COUNTRYSHORTNAME": "AFGHANISTAN",
    "CURRENCYCODE": "AFA",
    "TELEPHONECODE": 93,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AGO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AGO",
    "COUNTRYOFFICIALNAME": "PEOPLE'S REPUBLIC OF ANGOLA",
    "COUNTRYSHORTNAME": "ANGOLA",
    "CURRENCYCODE": "AON",
    "TELEPHONECODE": 244,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AIA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AIA",
    "COUNTRYOFFICIALNAME": "ANGUILLA",
    "COUNTRYSHORTNAME": "ANGUILLA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ALB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ALB",
    "COUNTRYOFFICIALNAME": "ALBANIA",
    "COUNTRYSHORTNAME": "ALBANIA",
    "CURRENCYCODE": "ALL",
    "TELEPHONECODE": 355,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AND",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AND",
    "COUNTRYOFFICIALNAME": "ANDORRA",
    "COUNTRYSHORTNAME": "ANDORRA",
    "CURRENCYCODE": "ADP",
    "TELEPHONECODE": 376,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ANT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ANT",
    "COUNTRYOFFICIALNAME": "NETHERLANDS ANTILLES",
    "COUNTRYSHORTNAME": "NETHERLANDS ANTI",
    "CURRENCYCODE": "ANG",
    "TELEPHONECODE": 599,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "2010-10-10 00:00:00.000",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ARE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ARE",
    "COUNTRYOFFICIALNAME": "UNITED ARAB EMIRATES",
    "COUNTRYSHORTNAME": "UNITED ARAB EMIR",
    "CURRENCYCODE": "AED",
    "TELEPHONECODE": 971,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ARG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ARG",
    "COUNTRYOFFICIALNAME": "ARGENTINA",
    "COUNTRYSHORTNAME": "ARGENTINA",
    "CURRENCYCODE": "ARS",
    "TELEPHONECODE": 54,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ARM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ARM",
    "COUNTRYOFFICIALNAME": "ARMENIA",
    "COUNTRYSHORTNAME": "ARMENIA",
    "CURRENCYCODE": "AMD",
    "TELEPHONECODE": 374,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ASM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ASM",
    "COUNTRYOFFICIALNAME": "SAMOA (AMERICAN)",
    "COUNTRYSHORTNAME": "SAMOA (AMERICAN)",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 684,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ATG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ATG",
    "COUNTRYOFFICIALNAME": "ANTIGUA AND BARBUDA",
    "COUNTRYSHORTNAME": "ANTIGUA&BARBUDA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AUS",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AUS",
    "COUNTRYOFFICIALNAME": "AUSTRALIA",
    "COUNTRYSHORTNAME": "AUSTRALIA",
    "CURRENCYCODE": "AUD",
    "TELEPHONECODE": 61,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AUT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AUT",
    "COUNTRYOFFICIALNAME": "AUSTRIA",
    "COUNTRYSHORTNAME": "AUSTRIA",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 43,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "AZE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "AZE",
    "COUNTRYOFFICIALNAME": "AZERBAIJAN",
    "COUNTRYSHORTNAME": "AZERBAIJAN",
    "CURRENCYCODE": "AZM",
    "TELEPHONECODE": 994,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BDI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BDI",
    "COUNTRYOFFICIALNAME": "BURUNDI",
    "COUNTRYSHORTNAME": "BURUNDI",
    "CURRENCYCODE": "BIF",
    "TELEPHONECODE": 257,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BEL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BEL",
    "COUNTRYOFFICIALNAME": "BELGIUM",
    "COUNTRYSHORTNAME": "BELGIUM",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 32,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BEN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BEN",
    "COUNTRYOFFICIALNAME": "BENIN",
    "COUNTRYSHORTNAME": "BENIN",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 229,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BFA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BFA",
    "COUNTRYOFFICIALNAME": "BURKINA FASO",
    "COUNTRYSHORTNAME": "BURKINA FASO",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 226,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BGD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BGD",
    "COUNTRYOFFICIALNAME": "PEOPLE'S REPUBLIC OF BANGLADESH",
    "COUNTRYSHORTNAME": "BANGLADESH",
    "CURRENCYCODE": "BDT",
    "TELEPHONECODE": 880,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BGR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BGR",
    "COUNTRYOFFICIALNAME": "BULGARIA",
    "COUNTRYSHORTNAME": "BULGARIA",
    "CURRENCYCODE": "BGL",
    "TELEPHONECODE": 359,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BHR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BHR",
    "COUNTRYOFFICIALNAME": "BAHRAIN",
    "COUNTRYSHORTNAME": "BAHRAIN",
    "CURRENCYCODE": "BHD",
    "TELEPHONECODE": 973,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BHS",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BHS",
    "COUNTRYOFFICIALNAME": "COMMONWEALTH OF THE BAHAMAS",
    "COUNTRYSHORTNAME": "BAHAMAS",
    "CURRENCYCODE": "BSD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BIH",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BIH",
    "COUNTRYOFFICIALNAME": "BOSNIA AND HERZEGOWINA",
    "COUNTRYSHORTNAME": "BOSNIA-HERZEGOV.",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 387,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BLR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BLR",
    "COUNTRYOFFICIALNAME": "BELARUS",
    "COUNTRYSHORTNAME": "BELARUS",
    "CURRENCYCODE": "BYR",
    "TELEPHONECODE": 375,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BLZ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BLZ",
    "COUNTRYOFFICIALNAME": "BELIZE",
    "COUNTRYSHORTNAME": "BELIZE",
    "CURRENCYCODE": "BZD",
    "TELEPHONECODE": 501,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BMU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BMU",
    "COUNTRYOFFICIALNAME": "BERMUDA",
    "COUNTRYSHORTNAME": "BERMUDA",
    "CURRENCYCODE": "BMD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BOL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BOL",
    "COUNTRYOFFICIALNAME": "BOLIVIA",
    "COUNTRYSHORTNAME": "BOLIVIA",
    "CURRENCYCODE": "BOB",
    "TELEPHONECODE": 591,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BRA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BRA",
    "COUNTRYOFFICIALNAME": "BRAZIL",
    "COUNTRYSHORTNAME": "BRAZIL",
    "CURRENCYCODE": "BRL",
    "TELEPHONECODE": 55,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BRB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BRB",
    "COUNTRYOFFICIALNAME": "BARBADOS",
    "COUNTRYSHORTNAME": "BARBADOS",
    "CURRENCYCODE": "BBD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BRN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BRN",
    "COUNTRYOFFICIALNAME": "BRUNEI DARUSSALAM",
    "COUNTRYSHORTNAME": "BRUNEI",
    "CURRENCYCODE": "BND",
    "TELEPHONECODE": 673,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BTN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BTN",
    "COUNTRYOFFICIALNAME": "BHUTAN",
    "COUNTRYSHORTNAME": "BHUTAN",
    "CURRENCYCODE": "BTN",
    "TELEPHONECODE": 975,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "BWA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "BWA",
    "COUNTRYOFFICIALNAME": "BOTSWANA",
    "COUNTRYSHORTNAME": "BOTSWANA",
    "CURRENCYCODE": "BWP",
    "TELEPHONECODE": 267,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CAF",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CAF",
    "COUNTRYOFFICIALNAME": "CENTRAL AFRICAN REPUBLIC",
    "COUNTRYSHORTNAME": "CENTRAL AFRICAN",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 236,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CAN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CAN",
    "COUNTRYOFFICIALNAME": "CANADA",
    "COUNTRYSHORTNAME": "CANADA",
    "CURRENCYCODE": "CAD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CHE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CHE",
    "COUNTRYOFFICIALNAME": "SWITZERLAND",
    "COUNTRYSHORTNAME": "SWITZERLAND",
    "CURRENCYCODE": "CHF",
    "TELEPHONECODE": 41,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CHL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CHL",
    "COUNTRYOFFICIALNAME": "CHILE",
    "COUNTRYSHORTNAME": "CHILE",
    "CURRENCYCODE": "CLF",
    "TELEPHONECODE": 56,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CHN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CHN",
    "COUNTRYOFFICIALNAME": "PEOPLE'S REPUBLIC OF CHINA",
    "COUNTRYSHORTNAME": "CHINA",
    "CURRENCYCODE": "CNY",
    "TELEPHONECODE": 86,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CIV",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CIV",
    "COUNTRYOFFICIALNAME": "IVORY COAST",
    "COUNTRYSHORTNAME": "IVORY COAST",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 225,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CMR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CMR",
    "COUNTRYOFFICIALNAME": "CAMEROON",
    "COUNTRYSHORTNAME": "CAMEROON",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 237,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "COD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "COD",
    "COUNTRYOFFICIALNAME": "CONGO, DEMOCRATIC REPUBLIC OF THE",
    "COUNTRYSHORTNAME": "RD CONGO",
    "CURRENCYCODE": "CDF",
    "TELEPHONECODE": 243,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "COG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "COG",
    "COUNTRYOFFICIALNAME": "The Republic of the CONGO (Congo-Brazzaville)",
    "COUNTRYSHORTNAME": "CONGO",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 242,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "COK",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "COK",
    "COUNTRYOFFICIALNAME": "COOK ISLANDS",
    "COUNTRYSHORTNAME": "COOK ISLANDS",
    "CURRENCYCODE": "NZD",
    "TELEPHONECODE": 682,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "COL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "COL",
    "COUNTRYOFFICIALNAME": "COLOMBIA",
    "COUNTRYSHORTNAME": "COLOMBIA",
    "CURRENCYCODE": "COP",
    "TELEPHONECODE": 57,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "COM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "COM",
    "COUNTRYOFFICIALNAME": "ISLAMIC FEDERAL REPUBLIC OF THE COMOROS",
    "COUNTRYSHORTNAME": "COMOROS",
    "CURRENCYCODE": "KMF",
    "TELEPHONECODE": 269,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CPV",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CPV",
    "COUNTRYOFFICIALNAME": "CAPE VERDE",
    "COUNTRYSHORTNAME": "CAPE VERDE",
    "CURRENCYCODE": "CVE",
    "TELEPHONECODE": 238,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CRI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CRI",
    "COUNTRYOFFICIALNAME": "COSTA RICA",
    "COUNTRYSHORTNAME": "COSTA RICA",
    "CURRENCYCODE": "CRC",
    "TELEPHONECODE": 506,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CUB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CUB",
    "COUNTRYOFFICIALNAME": "CUBA",
    "COUNTRYSHORTNAME": "CUBA",
    "CURRENCYCODE": "CUP",
    "TELEPHONECODE": 53,
    "EMBARGOSTATUS": 1,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CUW",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CUW",
    "COUNTRYOFFICIALNAME": "CURACAO",
    "COUNTRYSHORTNAME": "CURACAO",
    "CURRENCYCODE": "ANG",
    "TELEPHONECODE": 599,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CYM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CYM",
    "COUNTRYOFFICIALNAME": "CAYMAN ISLANDS",
    "COUNTRYSHORTNAME": "CAYMAN ISLANDS",
    "CURRENCYCODE": "KYD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CYP",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CYP",
    "COUNTRYOFFICIALNAME": "CYPRUS",
    "COUNTRYSHORTNAME": "CYPRUS",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 357,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "CZE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "CZE",
    "COUNTRYOFFICIALNAME": "CZECH REPUBLIC",
    "COUNTRYSHORTNAME": "CZECH REPUBLIC",
    "CURRENCYCODE": "CZK",
    "TELEPHONECODE": 420,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "DEU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "DEU",
    "COUNTRYOFFICIALNAME": "GERMANY",
    "COUNTRYSHORTNAME": "GERMANY",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 49,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "DJI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "DJI",
    "COUNTRYOFFICIALNAME": "DJIBOUTI",
    "COUNTRYSHORTNAME": "DJIBOUTI",
    "CURRENCYCODE": "DJF",
    "TELEPHONECODE": 253,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "DMA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "DMA",
    "COUNTRYOFFICIALNAME": "COMMONWEALTH OF DOMINICA",
    "COUNTRYSHORTNAME": "DOMINICA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "00",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "DNK",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "DNK",
    "COUNTRYOFFICIALNAME": "DENMARK",
    "COUNTRYSHORTNAME": "DENMARK",
    "CURRENCYCODE": "DKK",
    "TELEPHONECODE": 45,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "DOM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "DOM",
    "COUNTRYOFFICIALNAME": "DOMINICAN REPUBLIC",
    "COUNTRYSHORTNAME": "DOMINICAN REP.",
    "CURRENCYCODE": "DOP",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "DZA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "DZA",
    "COUNTRYOFFICIALNAME": "ALGERIA",
    "COUNTRYSHORTNAME": "ALGERIA",
    "CURRENCYCODE": "DZD",
    "TELEPHONECODE": 213,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ECU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ECU",
    "COUNTRYOFFICIALNAME": "ECUADOR",
    "COUNTRYSHORTNAME": "ECUADOR",
    "CURRENCYCODE": "ECS",
    "TELEPHONECODE": 593,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "EGY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "EGY",
    "COUNTRYOFFICIALNAME": "EGYPT",
    "COUNTRYSHORTNAME": "EGYPT",
    "CURRENCYCODE": "EGP",
    "TELEPHONECODE": 20,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ERI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ERI",
    "COUNTRYOFFICIALNAME": "ERITREA",
    "COUNTRYSHORTNAME": "ERITREA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 291,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ESP",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ESP",
    "COUNTRYOFFICIALNAME": "SPAIN",
    "COUNTRYSHORTNAME": "SPAIN",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 34,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "EST",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "EST",
    "COUNTRYOFFICIALNAME": "ESTONIA",
    "COUNTRYSHORTNAME": "ESTONIA",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 372,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ETH",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ETH",
    "COUNTRYOFFICIALNAME": "ETHIOPIA",
    "COUNTRYSHORTNAME": "ETHIOPIA",
    "CURRENCYCODE": "ETB",
    "TELEPHONECODE": 251,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "FIN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "FIN",
    "COUNTRYOFFICIALNAME": "FINLAND",
    "COUNTRYSHORTNAME": "FINLAND",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 358,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "FJI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "FJI",
    "COUNTRYOFFICIALNAME": "FIJI",
    "COUNTRYSHORTNAME": "FIJI",
    "CURRENCYCODE": "FJD",
    "TELEPHONECODE": 679,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "FRA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "FRA",
    "COUNTRYOFFICIALNAME": "FRANCE",
    "COUNTRYSHORTNAME": "FRANCE",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 33,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "FRO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "FRO",
    "COUNTRYOFFICIALNAME": "FAROE ISLANDS (THE)",
    "COUNTRYSHORTNAME": "FAROE ISLANDS",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 298,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "FSM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "FSM",
    "COUNTRYOFFICIALNAME": "FEDERATE STATES OF MICRONESIA",
    "COUNTRYSHORTNAME": "MICRONESIA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GAB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GAB",
    "COUNTRYOFFICIALNAME": "GABON",
    "COUNTRYSHORTNAME": "GABON",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 241,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GBR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GBR",
    "COUNTRYOFFICIALNAME": "UNITED KINGDOM",
    "COUNTRYSHORTNAME": "UNITED KINGDOM",
    "CURRENCYCODE": "GBP",
    "TELEPHONECODE": 44,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GEO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GEO",
    "COUNTRYOFFICIALNAME": "GEORGIA",
    "COUNTRYSHORTNAME": "GEORGIA",
    "CURRENCYCODE": "GEL",
    "TELEPHONECODE": 995,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GGY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GGY",
    "COUNTRYOFFICIALNAME": "GUERNSEY",
    "COUNTRYSHORTNAME": "GUERNSEY",
    "CURRENCYCODE": "GBP",
    "TELEPHONECODE": 44,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GHA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GHA",
    "COUNTRYOFFICIALNAME": "GHANA",
    "COUNTRYSHORTNAME": "GHANA",
    "CURRENCYCODE": "GHC",
    "TELEPHONECODE": 233,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GIB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GIB",
    "COUNTRYOFFICIALNAME": "GIBRALTAR",
    "COUNTRYSHORTNAME": "GIBRALTAR",
    "CURRENCYCODE": "GBP",
    "TELEPHONECODE": 350,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GIN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GIN",
    "COUNTRYOFFICIALNAME": "GUINEA",
    "COUNTRYSHORTNAME": "GUINEA",
    "CURRENCYCODE": "GNF",
    "TELEPHONECODE": 224,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GMB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GMB",
    "COUNTRYOFFICIALNAME": "GAMBIA",
    "COUNTRYSHORTNAME": "GAMBIA",
    "CURRENCYCODE": "GMD",
    "TELEPHONECODE": 220,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GNB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GNB",
    "COUNTRYOFFICIALNAME": "GUINEA-BISSAU",
    "COUNTRYSHORTNAME": "GUINEA-BISSAU",
    "CURRENCYCODE": "XOF",
    "TELEPHONECODE": 245,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GNQ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GNQ",
    "COUNTRYOFFICIALNAME": "EQUATORIAL GUINEA",
    "COUNTRYSHORTNAME": "EQUATOR. GUINEA",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 240,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GRC",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GRC",
    "COUNTRYOFFICIALNAME": "GREECE",
    "COUNTRYSHORTNAME": "GREECE",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 30,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GRD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GRD",
    "COUNTRYOFFICIALNAME": "GRENADA",
    "COUNTRYSHORTNAME": "GRENADA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GRL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GRL",
    "COUNTRYOFFICIALNAME": "GREENLAND",
    "COUNTRYSHORTNAME": "GROENLAND",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 299,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GTM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GTM",
    "COUNTRYOFFICIALNAME": "GUATEMALA",
    "COUNTRYSHORTNAME": "GUATEMALA",
    "CURRENCYCODE": "GTQ",
    "TELEPHONECODE": 502,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GUM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GUM",
    "COUNTRYOFFICIALNAME": "GUAM",
    "COUNTRYSHORTNAME": "GUAM",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "GUY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "GUY",
    "COUNTRYOFFICIALNAME": "GUYANA",
    "COUNTRYSHORTNAME": "GUYANA",
    "CURRENCYCODE": "GYD",
    "TELEPHONECODE": 592,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "HKG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "HKG",
    "COUNTRYOFFICIALNAME": "HONG KONG",
    "COUNTRYSHORTNAME": "HONG KONG",
    "CURRENCYCODE": "HKD",
    "TELEPHONECODE": 852,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "HND",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "HND",
    "COUNTRYOFFICIALNAME": "HONDURAS",
    "COUNTRYSHORTNAME": "HONDURAS",
    "CURRENCYCODE": "HNL",
    "TELEPHONECODE": 504,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "HRV",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "HRV",
    "COUNTRYOFFICIALNAME": "CROATIA",
    "COUNTRYSHORTNAME": "CROATIA",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 385,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "HTI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "HTI",
    "COUNTRYOFFICIALNAME": "HAITI",
    "COUNTRYSHORTNAME": "HAITI",
    "CURRENCYCODE": "HTG",
    "TELEPHONECODE": 509,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "HUN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "HUN",
    "COUNTRYOFFICIALNAME": "HUNGARY",
    "COUNTRYSHORTNAME": "HUNGARY",
    "CURRENCYCODE": "HUF",
    "TELEPHONECODE": 36,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IDN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IDN",
    "COUNTRYOFFICIALNAME": "INDONESIA",
    "COUNTRYSHORTNAME": "INDONESIA",
    "CURRENCYCODE": "IDR",
    "TELEPHONECODE": 62,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IMN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IMN",
    "COUNTRYOFFICIALNAME": "ISLE OF MAN",
    "COUNTRYSHORTNAME": "ISLE OF MAN",
    "CURRENCYCODE": "GBP",
    "TELEPHONECODE": 44,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IND",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IND",
    "COUNTRYOFFICIALNAME": "INDIA",
    "COUNTRYSHORTNAME": "INDIA",
    "CURRENCYCODE": "INR",
    "TELEPHONECODE": 91,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IOT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IOT",
    "COUNTRYOFFICIALNAME": "BRITISH INDIAN OCEAN TERRITORY",
    "COUNTRYSHORTNAME": "BRITISH IOT",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IRL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IRL",
    "COUNTRYOFFICIALNAME": "IRELAND",
    "COUNTRYSHORTNAME": "IRELAND",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 353,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IRN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IRN",
    "COUNTRYOFFICIALNAME": "ISLAMIC REPUBLIC OF IRAN",
    "COUNTRYSHORTNAME": "IRAN",
    "CURRENCYCODE": "IRR",
    "TELEPHONECODE": 98,
    "EMBARGOSTATUS": 1,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "IRQ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "IRQ",
    "COUNTRYOFFICIALNAME": "IRAQ",
    "COUNTRYSHORTNAME": "IRAQ",
    "CURRENCYCODE": "IQD",
    "TELEPHONECODE": 964,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ISL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ISL",
    "COUNTRYOFFICIALNAME": "ICELAND",
    "COUNTRYSHORTNAME": "ICELAND",
    "CURRENCYCODE": "ISK",
    "TELEPHONECODE": 354,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ISR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ISR",
    "COUNTRYOFFICIALNAME": "ISRAEL",
    "COUNTRYSHORTNAME": "ISRAEL",
    "CURRENCYCODE": "ILS",
    "TELEPHONECODE": 972,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ITA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ITA",
    "COUNTRYOFFICIALNAME": "ITALY",
    "COUNTRYSHORTNAME": "ITALY",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 39,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "JAM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "JAM",
    "COUNTRYOFFICIALNAME": "JAMAICA",
    "COUNTRYSHORTNAME": "JAMAICA",
    "CURRENCYCODE": "JMD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "JEY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "JEY",
    "COUNTRYOFFICIALNAME": "JERSEY",
    "COUNTRYSHORTNAME": "JERSEY",
    "CURRENCYCODE": "GBP",
    "TELEPHONECODE": 44,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "JOR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "JOR",
    "COUNTRYOFFICIALNAME": "JORDAN",
    "COUNTRYSHORTNAME": "JORDAN",
    "CURRENCYCODE": "JOD",
    "TELEPHONECODE": 962,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "JPN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "JPN",
    "COUNTRYOFFICIALNAME": "JAPAN",
    "COUNTRYSHORTNAME": "JAPAN",
    "CURRENCYCODE": "JPY",
    "TELEPHONECODE": 81,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KAZ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KAZ",
    "COUNTRYOFFICIALNAME": "KAZAKHSTAN",
    "COUNTRYSHORTNAME": "KAZAKHSTAN",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 7,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KEN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KEN",
    "COUNTRYOFFICIALNAME": "KENYA",
    "COUNTRYSHORTNAME": "KENYA",
    "CURRENCYCODE": "KES",
    "TELEPHONECODE": 254,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KGZ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KGZ",
    "COUNTRYOFFICIALNAME": "KYRGYZSTAN",
    "COUNTRYSHORTNAME": "KYRGYZSTAN",
    "CURRENCYCODE": "KGS",
    "TELEPHONECODE": 996,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KHM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KHM",
    "COUNTRYOFFICIALNAME": "CAMBODIA",
    "COUNTRYSHORTNAME": "CAMBODIA",
    "CURRENCYCODE": "KHR",
    "TELEPHONECODE": 855,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KIR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KIR",
    "COUNTRYOFFICIALNAME": "KIRIBATI",
    "COUNTRYSHORTNAME": "KIRIBATI",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 686,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KNA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KNA",
    "COUNTRYOFFICIALNAME": "SAINT KITTS AND NEVIS",
    "COUNTRYSHORTNAME": "SAINT KITTS ...",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KOR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KOR",
    "COUNTRYOFFICIALNAME": "SOUTH KOREA",
    "COUNTRYSHORTNAME": "KOREA SOUTH",
    "CURRENCYCODE": "KRW",
    "TELEPHONECODE": 82,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "KWT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "KWT",
    "COUNTRYOFFICIALNAME": "KUWAIT",
    "COUNTRYSHORTNAME": "KUWAIT",
    "CURRENCYCODE": "KWD",
    "TELEPHONECODE": 965,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LAO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LAO",
    "COUNTRYOFFICIALNAME": "PEOPLE'S DEMOCRATIC REPUBLIC OF LAO",
    "COUNTRYSHORTNAME": "LAO",
    "CURRENCYCODE": "LAK",
    "TELEPHONECODE": 856,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LBN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LBN",
    "COUNTRYOFFICIALNAME": "LEBANON",
    "COUNTRYSHORTNAME": "LEBANON",
    "CURRENCYCODE": "LBP",
    "TELEPHONECODE": 961,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LBR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LBR",
    "COUNTRYOFFICIALNAME": "LIBERIA",
    "COUNTRYSHORTNAME": "LIBERIA",
    "CURRENCYCODE": "LRD",
    "TELEPHONECODE": 231,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LBY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LBY",
    "COUNTRYOFFICIALNAME": "SOCIALIST PEOPLE'S LIBYAN ARAB JAMAHIRIYA",
    "COUNTRYSHORTNAME": "LIBYA",
    "CURRENCYCODE": "LYD",
    "TELEPHONECODE": 218,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LCA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LCA",
    "COUNTRYOFFICIALNAME": "SAINT LUCIA",
    "COUNTRYSHORTNAME": "SAINT LUCIA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LIE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LIE",
    "COUNTRYOFFICIALNAME": "LIECHTENSTEIN",
    "COUNTRYSHORTNAME": "LIECHTENSTEIN",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 423,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LKA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LKA",
    "COUNTRYOFFICIALNAME": "SRI LANKA",
    "COUNTRYSHORTNAME": "SRI LANKA",
    "CURRENCYCODE": "LKR",
    "TELEPHONECODE": 94,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LSO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LSO",
    "COUNTRYOFFICIALNAME": "LESOTHO",
    "COUNTRYSHORTNAME": "LESOTHO",
    "CURRENCYCODE": "LSL",
    "TELEPHONECODE": 266,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LTU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LTU",
    "COUNTRYOFFICIALNAME": "LITHUANIA",
    "COUNTRYSHORTNAME": "LITHUANIA",
    "CURRENCYCODE": "LTL",
    "TELEPHONECODE": 370,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LUX",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LUX",
    "COUNTRYOFFICIALNAME": "LUXEMBOURG",
    "COUNTRYSHORTNAME": "LUXEMBOURG",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 352,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "LVA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "LVA",
    "COUNTRYOFFICIALNAME": "LATVIA",
    "COUNTRYSHORTNAME": "LATVIA",
    "CURRENCYCODE": "LVL",
    "TELEPHONECODE": 371,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MAC",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MAC",
    "COUNTRYOFFICIALNAME": "MACAU",
    "COUNTRYSHORTNAME": "MACAU",
    "CURRENCYCODE": "MOP",
    "TELEPHONECODE": 853,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MAR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MAR",
    "COUNTRYOFFICIALNAME": "MOROCCO",
    "COUNTRYSHORTNAME": "MOROCCO",
    "CURRENCYCODE": "MAD",
    "TELEPHONECODE": 212,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MCO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MCO",
    "COUNTRYOFFICIALNAME": "MONACO",
    "COUNTRYSHORTNAME": "MONACO",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 377,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MDA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MDA",
    "COUNTRYOFFICIALNAME": "MOLDOVA",
    "COUNTRYSHORTNAME": "MOLDOVA",
    "CURRENCYCODE": "MDL",
    "TELEPHONECODE": 373,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MDG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MDG",
    "COUNTRYOFFICIALNAME": "MADAGASCAR",
    "COUNTRYSHORTNAME": "MADAGASCAR",
    "CURRENCYCODE": "MGF",
    "TELEPHONECODE": 261,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MDV",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MDV",
    "COUNTRYOFFICIALNAME": "MALDIVES",
    "COUNTRYSHORTNAME": "MALDIVES",
    "CURRENCYCODE": "MVR",
    "TELEPHONECODE": 960,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MEX",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MEX",
    "COUNTRYOFFICIALNAME": "MEXICO",
    "COUNTRYSHORTNAME": "MEXICO",
    "CURRENCYCODE": "MXN",
    "TELEPHONECODE": 52,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MHL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MHL",
    "COUNTRYOFFICIALNAME": "THE REPUBLIC OF THE MARSHALL ISLANDS",
    "COUNTRYSHORTNAME": "MARSHALL ISLANDS",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 584,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MKD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MKD",
    "COUNTRYOFFICIALNAME": "MACEDONIA",
    "COUNTRYSHORTNAME": "MACEDONIA",
    "CURRENCYCODE": "MKK",
    "TELEPHONECODE": 389,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MLI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MLI",
    "COUNTRYOFFICIALNAME": "MALI",
    "COUNTRYSHORTNAME": "MALI",
    "CURRENCYCODE": "MAF",
    "TELEPHONECODE": 223,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MLT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MLT",
    "COUNTRYOFFICIALNAME": "MALTA",
    "COUNTRYSHORTNAME": "MALTA",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 356,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MMR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MMR",
    "COUNTRYOFFICIALNAME": "BURMA/MYANMAR",
    "COUNTRYSHORTNAME": "BURMA",
    "CURRENCYCODE": "MMK",
    "TELEPHONECODE": 95,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MNE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MNE",
    "COUNTRYOFFICIALNAME": "Republic of MONTENEGRO",
    "COUNTRYSHORTNAME": "MONTENEGRO",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 382,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "2006-06-05 00:00:00.000",
    "REPLACEBY": ""
  },
  {
    "id": "MNG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MNG",
    "COUNTRYOFFICIALNAME": "MONGOLIA",
    "COUNTRYSHORTNAME": "MONGOLIA",
    "CURRENCYCODE": "MNT",
    "TELEPHONECODE": 976,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MNP",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MNP",
    "COUNTRYOFFICIALNAME": "MARIANA  NORTHERN ISLANDS (COMMONWEALTH OF)",
    "COUNTRYSHORTNAME": "MARIANA",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MOZ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MOZ",
    "COUNTRYOFFICIALNAME": "MOZAMBIQUE",
    "COUNTRYSHORTNAME": "MOZAMBIQUE",
    "CURRENCYCODE": "MZN",
    "TELEPHONECODE": 258,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MRT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MRT",
    "COUNTRYOFFICIALNAME": "MAURITANIA",
    "COUNTRYSHORTNAME": "MAURITANIA",
    "CURRENCYCODE": "MRO",
    "TELEPHONECODE": 222,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MSR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MSR",
    "COUNTRYOFFICIALNAME": "MONTSERRAT",
    "COUNTRYSHORTNAME": "MONTSERRAT",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MUS",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MUS",
    "COUNTRYOFFICIALNAME": "MAURITIUS",
    "COUNTRYSHORTNAME": "MAURITIUS",
    "CURRENCYCODE": "MUR",
    "TELEPHONECODE": 230,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MWI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MWI",
    "COUNTRYOFFICIALNAME": "MALAWI",
    "COUNTRYSHORTNAME": "MALAWI",
    "CURRENCYCODE": "MWK",
    "TELEPHONECODE": 265,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "MYS",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "MYS",
    "COUNTRYOFFICIALNAME": "MALAYSIA",
    "COUNTRYSHORTNAME": "MALAYSIA",
    "CURRENCYCODE": "MYR",
    "TELEPHONECODE": 60,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NAM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NAM",
    "COUNTRYOFFICIALNAME": "NAMIBIA",
    "COUNTRYSHORTNAME": "NAMIBIA",
    "CURRENCYCODE": "NAD",
    "TELEPHONECODE": 264,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NER",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NER",
    "COUNTRYOFFICIALNAME": "NIGER",
    "COUNTRYSHORTNAME": "NIGER",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 227,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NGA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NGA",
    "COUNTRYOFFICIALNAME": "NIGERIA",
    "COUNTRYSHORTNAME": "NIGERIA",
    "CURRENCYCODE": "NGN",
    "TELEPHONECODE": 234,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NIC",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NIC",
    "COUNTRYOFFICIALNAME": "NICARAGUA",
    "COUNTRYSHORTNAME": "NICARAGUA",
    "CURRENCYCODE": "NIO",
    "TELEPHONECODE": 505,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NLD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NLD",
    "COUNTRYOFFICIALNAME": "NETHERLANDS",
    "COUNTRYSHORTNAME": "NETHERLANDS",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 31,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NOR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NOR",
    "COUNTRYOFFICIALNAME": "NORWAY",
    "COUNTRYSHORTNAME": "NORWAY",
    "CURRENCYCODE": "NOK",
    "TELEPHONECODE": 47,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NPL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NPL",
    "COUNTRYOFFICIALNAME": "NEPAL",
    "COUNTRYSHORTNAME": "NEPAL",
    "CURRENCYCODE": "NPR",
    "TELEPHONECODE": 977,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NRU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NRU",
    "COUNTRYOFFICIALNAME": "NAURU",
    "COUNTRYSHORTNAME": "NAURU",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 674,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "NZL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "NZL",
    "COUNTRYOFFICIALNAME": "NEW ZEALAND",
    "COUNTRYSHORTNAME": "NEW ZEALAND",
    "CURRENCYCODE": "NZD",
    "TELEPHONECODE": 64,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "OMN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "OMN",
    "COUNTRYOFFICIALNAME": "OMAN",
    "COUNTRYSHORTNAME": "OMAN",
    "CURRENCYCODE": "OMR",
    "TELEPHONECODE": 968,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PAK",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PAK",
    "COUNTRYOFFICIALNAME": "PAKISTAN",
    "COUNTRYSHORTNAME": "PAKISTAN",
    "CURRENCYCODE": "PKR",
    "TELEPHONECODE": 92,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PAL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PAL",
    "COUNTRYOFFICIALNAME": "PALESTINE",
    "COUNTRYSHORTNAME": "PALESTINE",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PAN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PAN",
    "COUNTRYOFFICIALNAME": "PANAMA",
    "COUNTRYSHORTNAME": "PANAMA",
    "CURRENCYCODE": "PAB",
    "TELEPHONECODE": 507,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PCN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PCN",
    "COUNTRYOFFICIALNAME": "PITCAIRN",
    "COUNTRYSHORTNAME": "PITCAIRN",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 64,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PER",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PER",
    "COUNTRYOFFICIALNAME": "PERU",
    "COUNTRYSHORTNAME": "PERU",
    "CURRENCYCODE": "PEN",
    "TELEPHONECODE": 51,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PHL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PHL",
    "COUNTRYOFFICIALNAME": "PHILIPPINES",
    "COUNTRYSHORTNAME": "PHILIPPINES",
    "CURRENCYCODE": "PHP",
    "TELEPHONECODE": 63,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PLW",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PLW",
    "COUNTRYOFFICIALNAME": "Republic of PALAU",
    "COUNTRYSHORTNAME": "PALAU Republic",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 680,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PNG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PNG",
    "COUNTRYOFFICIALNAME": "PAPUA NEW GUINEA",
    "COUNTRYSHORTNAME": "PAPUA NEW GUINEA",
    "CURRENCYCODE": "PGK",
    "TELEPHONECODE": 675,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "POL",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "POL",
    "COUNTRYOFFICIALNAME": "POLAND",
    "COUNTRYSHORTNAME": "POLAND",
    "CURRENCYCODE": "PLN",
    "TELEPHONECODE": 48,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PRI",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PRI",
    "COUNTRYOFFICIALNAME": "PUERTO RICO",
    "COUNTRYSHORTNAME": "PUERTO RICO",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PRK",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PRK",
    "COUNTRYOFFICIALNAME": "DEMOCRATIC PEOPLE'S REPUBIC OF KOREA",
    "COUNTRYSHORTNAME": "KOREA NORTH",
    "CURRENCYCODE": "KPW",
    "TELEPHONECODE": 82,
    "EMBARGOSTATUS": 1,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PRT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PRT",
    "COUNTRYOFFICIALNAME": "PORTUGAL",
    "COUNTRYSHORTNAME": "PORTUGAL",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 351,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "PRY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "PRY",
    "COUNTRYOFFICIALNAME": "PARAGUAY",
    "COUNTRYSHORTNAME": "PARAGUAY",
    "CURRENCYCODE": "PYG",
    "TELEPHONECODE": 595,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "QAT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "QAT",
    "COUNTRYOFFICIALNAME": "QATAR",
    "COUNTRYSHORTNAME": "QATAR",
    "CURRENCYCODE": "QAR",
    "TELEPHONECODE": 974,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ROM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ROM",
    "COUNTRYOFFICIALNAME": "ROMANIA",
    "COUNTRYSHORTNAME": "ROMANIA",
    "CURRENCYCODE": "ROL",
    "TELEPHONECODE": 40,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "RUS",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "RUS",
    "COUNTRYOFFICIALNAME": "RUSSIA",
    "COUNTRYSHORTNAME": "RUSSIA",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 7,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "RWA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "RWA",
    "COUNTRYOFFICIALNAME": "RWANDA",
    "COUNTRYSHORTNAME": "RWANDA",
    "CURRENCYCODE": "RWF",
    "TELEPHONECODE": 250,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SAU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SAU",
    "COUNTRYOFFICIALNAME": "SAUDI ARABIA",
    "COUNTRYSHORTNAME": "SAUDI ARABIA",
    "CURRENCYCODE": "SAR",
    "TELEPHONECODE": 966,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SCG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SCG",
    "COUNTRYOFFICIALNAME": "YUGOSLAVIA",
    "COUNTRYSHORTNAME": "YUGOSLAVIA",
    "CURRENCYCODE": "YUN",
    "TELEPHONECODE": 381,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "2006-06-04 00:00:00.000",
    "REPLACEBY": "YUG"
  },
  {
    "id": "SDN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SDN",
    "COUNTRYOFFICIALNAME": "SUDAN",
    "COUNTRYSHORTNAME": "SUDAN",
    "CURRENCYCODE": "SDD",
    "TELEPHONECODE": 249,
    "EMBARGOSTATUS": 1,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SEN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SEN",
    "COUNTRYOFFICIALNAME": "SENEGAL",
    "COUNTRYSHORTNAME": "SENEGAL",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 221,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SGP",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SGP",
    "COUNTRYOFFICIALNAME": "SINGAPORE",
    "COUNTRYSHORTNAME": "SINGAPORE",
    "CURRENCYCODE": "SGD",
    "TELEPHONECODE": 65,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SGS",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SGS",
    "COUNTRYOFFICIALNAME": "SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS",
    "COUNTRYSHORTNAME": "SOUTH GEORGIA...",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SLB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SLB",
    "COUNTRYOFFICIALNAME": "SOLOMON ISLANDS",
    "COUNTRYSHORTNAME": "SOLOMON ISLANDS",
    "CURRENCYCODE": "SBD",
    "TELEPHONECODE": 677,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SLE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SLE",
    "COUNTRYOFFICIALNAME": "SIERRA LEONE",
    "COUNTRYSHORTNAME": "SIERRA LEONE",
    "CURRENCYCODE": "SLL",
    "TELEPHONECODE": 232,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SLV",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SLV",
    "COUNTRYOFFICIALNAME": "EL SALVADOR",
    "COUNTRYSHORTNAME": "EL SALVADOR",
    "CURRENCYCODE": "SVC",
    "TELEPHONECODE": 503,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SMR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SMR",
    "COUNTRYOFFICIALNAME": "SAN MARINO",
    "COUNTRYSHORTNAME": "SAN MARINO",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 378,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SOM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SOM",
    "COUNTRYOFFICIALNAME": "SOMALIA",
    "COUNTRYSHORTNAME": "SOMALIA",
    "CURRENCYCODE": "SOS",
    "TELEPHONECODE": 252,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SRB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SRB",
    "COUNTRYOFFICIALNAME": "Republic of SERBIA",
    "COUNTRYSHORTNAME": "SERBIA",
    "CURRENCYCODE": "RSD",
    "TELEPHONECODE": 381,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "2006-06-05 00:00:00.000",
    "REPLACEBY": "SCG"
  },
  {
    "id": "SSD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SSD",
    "COUNTRYOFFICIALNAME": "REPUBLIC OF SOUTH SUDAN",
    "COUNTRYSHORTNAME": "SOUTH SUDAN",
    "CURRENCYCODE": "SSP",
    "TELEPHONECODE": 211,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "STP",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "STP",
    "COUNTRYOFFICIALNAME": "SAO TOME AND PRINCIPE",
    "COUNTRYSHORTNAME": "SAO TOME",
    "CURRENCYCODE": "STD",
    "TELEPHONECODE": 239,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SUR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SUR",
    "COUNTRYOFFICIALNAME": "SURINAME",
    "COUNTRYSHORTNAME": "SURINAME",
    "CURRENCYCODE": "SRG",
    "TELEPHONECODE": 597,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SVK",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SVK",
    "COUNTRYOFFICIALNAME": "SLOVAKIA",
    "COUNTRYSHORTNAME": "SLOVAKIA",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 421,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SVN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SVN",
    "COUNTRYOFFICIALNAME": "SLOVENIA",
    "COUNTRYSHORTNAME": "SLOVENIA",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 386,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SWE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SWE",
    "COUNTRYOFFICIALNAME": "SWEDEN",
    "COUNTRYSHORTNAME": "SWEDEN",
    "CURRENCYCODE": "SEK",
    "TELEPHONECODE": 46,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SWZ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SWZ",
    "COUNTRYOFFICIALNAME": "SWAZILAND",
    "COUNTRYSHORTNAME": "SWAZILAND",
    "CURRENCYCODE": "SZL",
    "TELEPHONECODE": 268,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SXM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SXM",
    "COUNTRYOFFICIALNAME": "SINT MAARTEN",
    "COUNTRYSHORTNAME": "SINT MAARTEN",
    "CURRENCYCODE": "ANG",
    "TELEPHONECODE": 599,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SYC",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SYC",
    "COUNTRYOFFICIALNAME": "SEYCHELLES",
    "COUNTRYSHORTNAME": "SEYCHELLES",
    "CURRENCYCODE": "SCR",
    "TELEPHONECODE": 248,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "SYR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "SYR",
    "COUNTRYOFFICIALNAME": "SYRIA",
    "COUNTRYSHORTNAME": "SYRIA",
    "CURRENCYCODE": "SYP",
    "TELEPHONECODE": 963,
    "EMBARGOSTATUS": 1,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TCA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TCA",
    "COUNTRYOFFICIALNAME": "TURKS AND CAICOS ISLANDS",
    "COUNTRYSHORTNAME": "TURKS CAICOS",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TCD",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TCD",
    "COUNTRYOFFICIALNAME": "CHAD",
    "COUNTRYSHORTNAME": "CHAD",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 235,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TGO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TGO",
    "COUNTRYOFFICIALNAME": "TOGO",
    "COUNTRYSHORTNAME": "TOGO",
    "CURRENCYCODE": "XAF",
    "TELEPHONECODE": 228,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "THA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "THA",
    "COUNTRYOFFICIALNAME": "THAILAND",
    "COUNTRYSHORTNAME": "THAILAND",
    "CURRENCYCODE": "THB",
    "TELEPHONECODE": 66,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TJK",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TJK",
    "COUNTRYOFFICIALNAME": "TAJIKISTAN",
    "COUNTRYSHORTNAME": "TAJIKISTAN",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 7,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TKM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TKM",
    "COUNTRYOFFICIALNAME": "TURKMENISTAN",
    "COUNTRYSHORTNAME": "TURKMENISTAN",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 993,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TON",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TON",
    "COUNTRYOFFICIALNAME": "TONGA",
    "COUNTRYSHORTNAME": "TONGA",
    "CURRENCYCODE": "TOP",
    "TELEPHONECODE": 676,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TTO",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TTO",
    "COUNTRYOFFICIALNAME": "TRINIDAD AND TOBAGO",
    "COUNTRYSHORTNAME": "TRINIDAD&TOBAGO",
    "CURRENCYCODE": "TTD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TUN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TUN",
    "COUNTRYOFFICIALNAME": "TUNISIA",
    "COUNTRYSHORTNAME": "TUNISIA",
    "CURRENCYCODE": "TND",
    "TELEPHONECODE": 216,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TUR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TUR",
    "COUNTRYOFFICIALNAME": "TURKEY",
    "COUNTRYSHORTNAME": "TURKEY",
    "CURRENCYCODE": "TRY",
    "TELEPHONECODE": 90,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TWN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TWN",
    "COUNTRYOFFICIALNAME": "TAIWAN",
    "COUNTRYSHORTNAME": "TAIWAN",
    "CURRENCYCODE": "TWD",
    "TELEPHONECODE": 886,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "TZA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "TZA",
    "COUNTRYOFFICIALNAME": "TANZANIA",
    "COUNTRYSHORTNAME": "TANZANIA",
    "CURRENCYCODE": "TZS",
    "TELEPHONECODE": 255,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "UGA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "UGA",
    "COUNTRYOFFICIALNAME": "UGANDA",
    "COUNTRYSHORTNAME": "UGANDA",
    "CURRENCYCODE": "UGS",
    "TELEPHONECODE": 256,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "UKR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "UKR",
    "COUNTRYOFFICIALNAME": "UKRAINE",
    "COUNTRYSHORTNAME": "UKRAINE",
    "CURRENCYCODE": "UAH",
    "TELEPHONECODE": 380,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "URY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "URY",
    "COUNTRYOFFICIALNAME": "URUGUAY",
    "COUNTRYSHORTNAME": "URUGUAY",
    "CURRENCYCODE": "UYP",
    "TELEPHONECODE": 598,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USA",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USA",
    "COUNTRYOFFICIALNAME": "UNITED STATES OF AMERICA",
    "COUNTRYSHORTNAME": "USA",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USU",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USU",
    "COUNTRYOFFICIALNAME": "U.S. Midwest",
    "COUNTRYSHORTNAME": "U.S. Midwest",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USV",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USV",
    "COUNTRYOFFICIALNAME": "U.S. Northeast",
    "COUNTRYSHORTNAME": "U.S. Northeast",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USW",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USW",
    "COUNTRYOFFICIALNAME": "U.S. West",
    "COUNTRYSHORTNAME": "U.S. West",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USX",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USX",
    "COUNTRYOFFICIALNAME": "U S. Mid-atlantic",
    "COUNTRYSHORTNAME": "U S. Mid-atlanti",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USY",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USY",
    "COUNTRYOFFICIALNAME": "U.S. Southeast",
    "COUNTRYSHORTNAME": "U.S. Southeast",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "USZ",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "USZ",
    "COUNTRYOFFICIALNAME": "U.S. South Central",
    "COUNTRYSHORTNAME": "U.S. South Centr",
    "CURRENCYCODE": "USD",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "UZB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "UZB",
    "COUNTRYOFFICIALNAME": "UZBEKISTAN",
    "COUNTRYSHORTNAME": "UZBEKISTAN",
    "CURRENCYCODE": "UZS",
    "TELEPHONECODE": 998,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VAT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VAT",
    "COUNTRYOFFICIALNAME": "VATICAN",
    "COUNTRYSHORTNAME": "VATICAN",
    "CURRENCYCODE": "EUR",
    "TELEPHONECODE": 39,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VCT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VCT",
    "COUNTRYOFFICIALNAME": "SAINT VINCENT AND THE GRENADINES",
    "COUNTRYSHORTNAME": "SAINT VINCENT...",
    "CURRENCYCODE": "",
    "TELEPHONECODE": 1,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VEN",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VEN",
    "COUNTRYOFFICIALNAME": "VENEZUELA",
    "COUNTRYSHORTNAME": "VENEZUELA",
    "CURRENCYCODE": "VEB",
    "TELEPHONECODE": 58,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VGB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VGB",
    "COUNTRYOFFICIALNAME": "VIRGIN ISLANDS (BRITISH)",
    "COUNTRYSHORTNAME": "VIRGIN (GBR)",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VIR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VIR",
    "COUNTRYOFFICIALNAME": "UNITED STATES VIRGIN ISLANDS",
    "COUNTRYSHORTNAME": "VIRGIN (USA)",
    "CURRENCYCODE": "",
    "TELEPHONECODE": "000",
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VNM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VNM",
    "COUNTRYOFFICIALNAME": "VIET NAM",
    "COUNTRYSHORTNAME": "VIET NAM",
    "CURRENCYCODE": "VND",
    "TELEPHONECODE": 84,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "VUT",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "VUT",
    "COUNTRYOFFICIALNAME": "VANUATU",
    "COUNTRYSHORTNAME": "VANUATU",
    "CURRENCYCODE": "VUV",
    "TELEPHONECODE": 678,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "WSM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "WSM",
    "COUNTRYOFFICIALNAME": "SAMOA",
    "COUNTRYSHORTNAME": "SAMOA",
    "CURRENCYCODE": "WST",
    "TELEPHONECODE": 685,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "YEM",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "YEM",
    "COUNTRYOFFICIALNAME": "YEMEN",
    "COUNTRYSHORTNAME": "YEMEN",
    "CURRENCYCODE": "YER",
    "TELEPHONECODE": 967,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "YUG",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "YUG",
    "COUNTRYOFFICIALNAME": "YUGOSLAVIA Old",
    "COUNTRYSHORTNAME": "YUGOSLAVIA Old",
    "CURRENCYCODE": "YUN",
    "TELEPHONECODE": 381,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ZAF",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ZAF",
    "COUNTRYOFFICIALNAME": "SOUTH AFRICA",
    "COUNTRYSHORTNAME": "SOUTH AFRICA",
    "CURRENCYCODE": "ZAR",
    "TELEPHONECODE": 27,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ZAR",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ZAR",
    "COUNTRYOFFICIALNAME": "ZAIRE Old",
    "COUNTRYSHORTNAME": "ZAIRE Old",
    "CURRENCYCODE": "ZRN",
    "TELEPHONECODE": 243,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "1997-05-17 00:00:00.000",
    "INCEPTIONDATE": "",
    "REPLACEBY": "COD"
  },
  {
    "id": "ZMB",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ZMB",
    "COUNTRYOFFICIALNAME": "ZAMBIA",
    "COUNTRYSHORTNAME": "ZAMBIA",
    "CURRENCYCODE": "ZMK",
    "TELEPHONECODE": 260,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  },
  {
    "id": "ZWE",
    "ISACTIVE": 1,
    "LASTSYNC": "2019-02-22 14:14:34.723",
    "COUNTRYCODE": "ZWE",
    "COUNTRYOFFICIALNAME": "ZIMBABWE",
    "COUNTRYSHORTNAME": "ZIMBABWE",
    "CURRENCYCODE": "ZWD",
    "TELEPHONECODE": 263,
    "EMBARGOSTATUS": 0,
    "EXPIRYDATE": "",
    "INCEPTIONDATE": "",
    "REPLACEBY": ""
  }
];

export const Data = {countries, uwUnit, cedant, coutryAlt};
