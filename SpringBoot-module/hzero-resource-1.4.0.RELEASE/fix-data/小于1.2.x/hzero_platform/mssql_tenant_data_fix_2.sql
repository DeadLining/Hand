use hzero_platform;
UPDATE hdta SET hdta.tenant_id = hdt.tenant_id
FROM hiam_doc_type_assign hdta, hiam_doc_type hdt
WHERE hdt.doc_type_id = hdta.doc_type_id;

UPDATE hdtad SET hdtad.tenant_id = hdt.tenant_id
FROM hiam_doc_type_auth_dim hdtad, hiam_doc_type hdt
WHERE hdt.doc_type_id = hdtad.doc_type_id;

UPDATE hdtp SET hdtp.tenant_id = hdtad.tenant_id
FROM hiam_doc_type_permission hdtp, hiam_doc_type_auth_dim hdtad
WHERE hdtp.auth_dim_id = hdtad.auth_dim_id;

UPDATE hra SET hra.tenant_id = hdt.tenant_id
FROM hiam_role_authority hra, hiam_doc_type hdt
WHERE hdt.doc_type_id = hra.auth_doc_type_id;

UPDATE hral SET hral.tenant_id = hra.tenant_id
FROM hiam_role_authority_line hral, hiam_role_authority hra
WHERE hral.role_auth_id = hra.role_auth_id;

UPDATE hsgt SET hsgt.tenant_id = hsg.tenant_id
FROM hiam_sec_grp_tl hsgt, hiam_sec_grp hsg
WHERE hsg.sec_grp_id = hsgt.sec_grp_id;

UPDATE huoa SET huoa.tenant_id = iu.organization_id
FROM hiam_user_open_account huoa, iam_user iu
WHERE iu.login_name = huoa.username;

UPDATE huoa SET huoa.tenant_id = iu.organization_id
FROM hiam_user_open_account huoa, iam_user iu
WHERE iu.login_name = huoa.username;

UPDATE hcrd SET hcrd.tenant_id = hcr.tenant_id
FROM hpfm_code_rule_dist hcrd, hpfm_code_rule hcr
WHERE hcr.rule_id = hcrd.rule_id;

UPDATE hcrd SET hcrd.tenant_id = hcrd1.tenant_id
FROM hpfm_code_rule_detail hcrd, hpfm_code_rule_dist hcrd1
WHERE hcrd.rule_dist_id = hcrd1.rule_dist_id;

UPDATE hcrv SET hcrv.tenant_id = hcrd.tenant_id
FROM hpfm_code_rule_value hcrv, hpfm_code_rule_detail hcrd
WHERE hcrd.rule_detail_id = hcrv.rule_detail_id;

UPDATE hct SET hct.tenant_id = hc.tenant_id
FROM hpfm_country_tl hct, hpfm_country hc
WHERE hct.country_id = hc.country_id;

UPDATE hct SET hct.tenant_id = hc.tenant_id
FROM hpfm_currency_tl hct, hpfm_currency hc
WHERE hct.currency_id = hc.currency_id;

UPDATE hcrp SET hcrp.tenant_id = hcr.tenant_id
FROM hpfm_customize_range_point hcrp, hpfm_customize_range hcr
WHERE hcr.range_id = hcrp.range_id;

UPDATE hcrr SET hcrr.tenant_id = hcr.tenant_id
FROM hpfm_customize_range_rule hcrr, hpfm_customize_range hcr
WHERE hcr.range_id = hcrr.range_id;

UPDATE hdcc SET hdcc.tenant_id = hdc.tenant_id
FROM hpfm_dashboard_card_clause hdcc, hpfm_dashboard_card hdc
WHERE hdcc.card_id = hdc.id;

UPDATE hdct SET hdct.tenant_id = hdc.tenant_id
FROM hpfm_dashboard_card_tl hdct, hpfm_dashboard_card hdc
WHERE hdc.id = hdct.id;

UPDATE hdct SET hdct.tenant_id = hdc.tenant_id
FROM hpfm_dashboard_clause_tl hdct, hpfm_dashboard_clause hdc
WHERE hdct.clause_id = hdc.clause_id;

UPDATE hdrc SET hdrc.tenant_id = hdc.tenant_id
FROM hpfm_dashboard_role_card hdrc, hpfm_dashboard_card hdc
WHERE hdc.id = hdrc.card_id;

UPDATE hd SET hd.tenant_id = hd1.tenant_id
FROM hpfm_database hd, hpfm_datasource hd1
WHERE hd.datasource_id = hd1.datasource_id;

UPDATE hds SET hds.tenant_id = hd.tenant_id
FROM hpfm_datasource_service hds, hpfm_datasource hd
WHERE hds.datasource_id = hd.datasource_id;

UPDATE hdis SET hdis.tenant_id = hdil.tenant_id
FROM hpfm_db_ide_script hdis, hpfm_db_ide_log hdil
WHERE hdis.log_id = hdil.log_id;

UPDATE her SET her.tenant_id = he.tenant_id
FROM hpfm_event_rule her, hpfm_event he
WHERE her.event_id = he.event_id;

UPDATE hfht SET hfht.tenant_id = hfh.tenant_id
FROM hpfm_form_header_tl hfht, hpfm_form_header hfh
WHERE hfht.form_header_id = hfh.form_header_id;

UPDATE hflt SET hflt.tenant_id = hfl.tenant_id
FROM hpfm_form_line_tl hflt, hpfm_form_line hfl
WHERE hflt.form_line_id = hfl.form_line_id;

UPDATE hpr SET hpr.tenant_id = hpr1.tenant_id
FROM hpfm_permission_rel hpr, hpfm_permission_rule hpr1
WHERE hpr.rule_id = hpr1.rule_id;

UPDATE hpv SET hpr.tenant_id = hp.tenant_id
FROM hpfm_profile_value hpv, hpfm_profile hp
WHERE hp.profile_id = hpv.profile_id;
UPDATE imp SET imp.tenant_id = im.h_tenant_id
FROM iam_menu_permission imp, iam_menu im
WHERE im.id = imp.menu_id;

UPDATE imt SET imt.h_tenant_id = im.h_tenant_id
FROM iam_menu_tl imt, iam_menu im
WHERE im.id = imt.id;

UPDATE irp SET irp.tenant_id = ir.h_tenant_id
FROM iam_role_permission irp, iam_role ir
WHERE ir.id = irp.role_id;

UPDATE irt SET irt.h_tenant_id = ir.h_tenant_id
FROM iam_role_tl irt, iam_role ir
WHERE ir.id = irt.id;

UPDATE olh SET olh.tenant_id = ol.organization_id
FROM oauth_ldap_history olh, oauth_ldap ol
WHERE olh.ldap_id = ol.id;

UPDATE oleu SET oleu.tenant_id = olh.tenant_id
FROM oauth_ldap_error_user oleu, oauth_ldap_history olh
WHERE oleu.LDAP_HISTORY_ID = olh.id;

UPDATE oph SET oph.tenant_id = iu.organization_id
FROM oauth_password_history oph, iam_user iu
WHERE iu.id = oph.user_id;

