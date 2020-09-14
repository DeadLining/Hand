use hzero_platform;
UPDATE hiam_doc_type_assign hdta
SET hdta.tenant_id = ( SELECT hdt.tenant_id FROM hiam_doc_type hdt WHERE hdt.doc_type_id = hdta.doc_type_id)
WHERE hdta.doc_type_id in ( SELECT doc_type_id FROM hiam_doc_type);

UPDATE hiam_doc_type_auth_dim hdtad
SET hdtad.tenant_id = ( SELECT hdt.tenant_id FROM hiam_doc_type hdt WHERE hdt.doc_type_id = hdtad.doc_type_id)
WHERE hdtad.doc_type_id in ( SELECT doc_type_id FROM hiam_doc_type);

UPDATE hiam_doc_type_permission hdtp
SET hdtp.tenant_id = ( SELECT hdtad.tenant_id FROM hiam_doc_type_auth_dim hdtad WHERE hdtad.auth_dim_id = hdtp.auth_dim_id)
WHERE hdtp.auth_dim_id in ( SELECT auth_dim_id FROM hiam_doc_type_auth_dim);

UPDATE hiam_role_authority hra
SET hra.tenant_id = ( SELECT hdt.tenant_id FROM hiam_doc_type hdt WHERE hdt.doc_type_id = hra.auth_doc_type_id)
WHERE hra.auth_doc_type_id in ( SELECT doc_type_id FROM hiam_doc_type);

UPDATE hiam_role_authority_line hral
SET hral.tenant_id = ( SELECT hra.tenant_id FROM hiam_role_authority hra WHERE hral.role_auth_id = hra.role_auth_id)
WHERE hral.role_auth_id in ( SELECT role_auth_id FROM hiam_role_authority);

UPDATE hiam_sec_grp_tl hsgt
SET hsgt.tenant_id = ( SELECT hsg.tenant_id FROM hiam_sec_grp hsg WHERE hsg.sec_grp_id = hsgt.sec_grp_id)
WHERE hsgt.sec_grp_id in ( SELECT sec_grp_id FROM hiam_sec_grp);

UPDATE hiam_user_open_account huoa
SET huoa.tenant_id = ( SELECT iu.organization_id FROM iam_user iu WHERE iu.login_name = huoa.username)
WHERE huoa.username in ( SELECT login_name FROM iam_user);

UPDATE hiam_user_info hui
SET hui.tenant_id = ( SELECT iu.organization_id FROM iam_user iu WHERE iu.id = hui.user_id)
WHERE hui.user_id in ( SELECT id FROM iam_user);

UPDATE hpfm_code_rule_dist hcrd
SET hcrd.tenant_id = ( SELECT hcr.tenant_id FROM hpfm_code_rule hcr WHERE hcr.rule_id = hcrd.rule_id)
WHERE hcrd.rule_id in ( SELECT rule_id FROM hpfm_code_rule);

UPDATE hpfm_code_rule_detail hcrd
SET hcrd.tenant_id = ( SELECT hcrd1.tenant_id FROM hpfm_code_rule_dist hcrd1 WHERE hcrd.rule_dist_id = hcrd1.rule_dist_id)
WHERE hcrd.rule_dist_id in ( SELECT rule_dist_id FROM hpfm_code_rule_dist);

UPDATE hpfm_code_rule_value hcrv
SET hcrv.tenant_id = ( SELECT hcrd.tenant_id FROM hpfm_code_rule_detail hcrd WHERE hcrd.rule_detail_id = hcrv.rule_detail_id)
WHERE hcrv.rule_detail_id in ( SELECT rule_detail_id FROM hpfm_code_rule_detail);

UPDATE hpfm_country_tl hct
SET hct.tenant_id = ( SELECT hc.tenant_id FROM hpfm_country hc WHERE hct.country_id = hc.country_id)
WHERE hct.country_id in ( SELECT country_id FROM hpfm_country);

UPDATE hpfm_currency_tl hct
SET hct.tenant_id = ( SELECT hc.tenant_id FROM hpfm_currency hc WHERE hct.currency_id = hc.currency_id)
WHERE hct.currency_id in ( SELECT currency_id FROM hpfm_currency);

UPDATE hpfm_customize_range_point hcrp
SET hcrp.tenant_id = ( SELECT hcr.tenant_id FROM hpfm_customize_range hcr WHERE hcr.range_id = hcrp.range_id)
WHERE hcrp.range_id in ( SELECT range_id FROM hpfm_customize_range);

UPDATE hpfm_customize_range_rule hcrr
SET hcrr.tenant_id = ( SELECT hcr.tenant_id FROM hpfm_customize_range hcr WHERE hcr.range_id = hcrr.range_id)
WHERE hcrr.range_id in ( SELECT range_id FROM hpfm_customize_range);

UPDATE hpfm_dashboard_card_clause hdcc
SET hdcc.tenant_id = ( SELECT hdc.tenant_id FROM hpfm_dashboard_card hdc WHERE hdcc.card_id = hdc.id)
WHERE hdcc.card_id in ( SELECT id FROM hpfm_dashboard_card);

UPDATE hpfm_dashboard_card_tl hdct
SET hdct.tenant_id = ( SELECT hdc.tenant_id FROM hpfm_dashboard_card hdc WHERE hdc.id = hdct.id)
WHERE hdct.id in ( SELECT id FROM hpfm_dashboard_card);

UPDATE hpfm_dashboard_clause_tl hdct
SET hdct.tenant_id = ( SELECT hdc.tenant_id FROM hpfm_dashboard_clause hdc WHERE hdct.clause_id = hdc.clause_id)
WHERE hdct.clause_id in ( SELECT clause_id FROM hpfm_dashboard_clause);

UPDATE hpfm_dashboard_role_card hdrc
SET hdrc.tenant_id = ( SELECT hdc.tenant_id FROM hpfm_dashboard_card hdc WHERE hdc.id = hdrc.card_id)
WHERE hdrc.card_id in ( SELECT id FROM hpfm_dashboard_card);

UPDATE hpfm_database hd
SET hd.tenant_id = ( SELECT hd1.tenant_id FROM hpfm_datasource hd1 WHERE hd.datasource_id = hd1.datasource_id)
WHERE hd.datasource_id in ( SELECT datasource_id FROM hpfm_datasource);

UPDATE hpfm_datasource_service hds
SET hds.tenant_id = ( SELECT hd.tenant_id FROM hpfm_datasource hd WHERE hds.datasource_id = hd.datasource_id)
WHERE hds.datasource_id in ( SELECT datasource_id FROM hpfm_datasource);

UPDATE hpfm_db_ide_script hdis
SET hdis.tenant_id = ( SELECT hdil.tenant_id FROM hpfm_db_ide_log hdil WHERE hdis.log_id = hdil.log_id)
WHERE hdis.log_id in ( SELECT log_id FROM hpfm_db_ide_log);

UPDATE hpfm_event_rule her
SET her.tenant_id = ( SELECT he.tenant_id FROM hpfm_event he WHERE her.event_id = he.event_id)
WHERE her.event_id in ( SELECT event_id FROM hpfm_event);

UPDATE hpfm_form_header_tl hfht
SET hfht.tenant_id = ( SELECT hfh.tenant_id FROM hpfm_form_header hfh WHERE hfht.form_header_id = hfh.form_header_id)
WHERE hfht.form_header_id in ( SELECT form_header_id FROM hpfm_form_header);

UPDATE hpfm_form_line_tl hflt
SET hflt.tenant_id = ( SELECT hfl.tenant_id FROM hpfm_form_line hfl WHERE hflt.form_line_id = hfl.form_line_id)
WHERE hflt.form_line_id in ( SELECT form_line_id FROM hpfm_form_line);

UPDATE hpfm_permission_rel hpr
SET hpr.tenant_id = ( SELECT hpr1.tenant_id FROM hpfm_permission_rule hpr1 WHERE hpr.rule_id = hpr1.rule_id)
WHERE hpr.rule_id in ( SELECT rule_id FROM hpfm_permission_rule);

UPDATE hpfm_profile_value hpv
SET hpv.tenant_id = ( SELECT hp.tenant_id FROM hpfm_profile hp WHERE hp.profile_id = hpv.profile_id)
WHERE hpv.profile_id in ( SELECT profile_id FROM hpfm_profile);

UPDATE iam_menu_permission imp
SET imp.tenant_id = ( SELECT im.h_tenant_id FROM iam_menu im WHERE im.id = imp.menu_id)
WHERE imp.menu_id in ( SELECT id FROM iam_menu);

UPDATE iam_menu_tl imt
SET imt.h_tenant_id = ( SELECT im.h_tenant_id FROM iam_menu im WHERE im.id = imt.id)
WHERE imt.id in ( SELECT id FROM iam_menu);

UPDATE iam_role_permission irp
SET irp.tenant_id = ( SELECT ir.h_tenant_id FROM iam_role ir WHERE ir.id = irp.role_id)
WHERE irp.role_id in ( SELECT id FROM iam_role);

UPDATE iam_role_tl irt
SET irt.h_tenant_id = ( SELECT ir.h_tenant_id FROM iam_role ir WHERE ir.id = irt.id)
WHERE irt.id in ( SELECT id FROM iam_role);

UPDATE oauth_ldap_history olh
SET olh.tenant_id = ( SELECT ol.organization_id FROM oauth_ldap ol WHERE olh.ldap_id = ol.id)
WHERE olh.ldap_id in ( SELECT id FROM oauth_ldap);

UPDATE oauth_ldap_error_user oleu
SET oleu.tenant_id = ( SELECT olh.tenant_id FROM oauth_ldap_history olh WHERE oleu.LDAP_HISTORY_ID = olh.id)
WHERE oleu.LDAP_HISTORY_ID in ( SELECT id FROM oauth_ldap_history);

UPDATE oauth_password_history oph
SET oph.tenant_id = ( SELECT iu.organization_id FROM iam_user iu WHERE iu.id = oph.user_id)
WHERE oph.user_id in ( SELECT id FROM iam_user);
