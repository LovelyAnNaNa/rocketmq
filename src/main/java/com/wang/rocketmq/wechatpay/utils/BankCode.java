package com.wang.rocketmq.wechatpay.utils;

import lombok.Getter;

@Getter
public enum BankCode {
    ICBC("工商银行",1002),
    ABCHINA("农业银行",1005),
    CCB("建设银行",1003),
    BOC("中国银行",1026),
    BANKCOMM("交通银行", 1020),
    CMBCHINA("招商银行",1001),
    PSBC("邮储银行",1066),
    CMBC("民生银行",1006),
    PINGAN("平安银行",1010),
    CITICBANK("中信银行",1021),
    SPDB("浦发银行",1004),
    CIB("兴业银行",1009),
    CEBBANK("光大银行",1022),
    CGBCHINA("广发银行",1027),
    HXB("华夏银行",1025),
    NBCB("宁波银行",1056),
    BANKOFBEIJING("北京银行",4836),
    BOSC("上海银行",1024),
    NJCB("南京银行",1054),
    RHCZBANK("长子县融汇村镇银行",4755),
    CSCB("长沙银行", 4216),
    ZJTLCB("浙江泰隆商业银行", 4051),
    ZYBANK("中原银行",4753),
    IBKCN("企业银行（中国）",4761),
    SDEBANK("顺德农商银行",4036),
    HENGSHUIBANK("衡水银行",4752),
    CZCCB("长治银行",4756),
    CHNDTB("大同银行",4767),
    HNNX("河南省农村信用社",4115),
    BANKYELLOWRIVER("宁夏黄河农村商业银行",4150),
    SHANXINJ("山西省农村信用社",4156),
    AHRCU("安徽省农村信用社",4166),
    GSRCU("甘肃省农村信用社",4157),
    TRCBANK("天津农村商业银行",4153),
    GX966888("广西壮族自治区农村信用社",4113),
    SXNXS("陕西省农村信用社",4108),
    SZNS("深圳农村商业银行",4076),
    BEEB("宁波鄞州农村商业银行",4052),
    ZJ96596("浙江省农村信用社联合社",4764),
    JSNX("江苏省农村信用社联合社",4217),
    ZJRCBANK("江苏紫金农村商业银行股份有限公司",4072),
    ZGCBANK("北京中关村银行股份有限公司",4769),
    DBS("星展银行（中国）有限公司",4778),
    ZZBANK("枣庄银行股份有限公司",4766),
    UNITEDBANK("海口联合农村商业银行股份有限公司",4758),
    NCBCHINA("南洋商业银行（中国）有限公司",4763)
    ;
    private String bankName;
    private Integer code;
    private BankCode(String bankName, Integer code){
        this.bankName = bankName;
        this.code = code;
    }

    public static Integer getCodeByName(String bankName){
        BankCode[] values = BankCode.values();
        for (BankCode value : values) {
            if(value.getBankName().equals(bankName)){
                return value.getCode();
            }
        }
        return -1;
    }

}
