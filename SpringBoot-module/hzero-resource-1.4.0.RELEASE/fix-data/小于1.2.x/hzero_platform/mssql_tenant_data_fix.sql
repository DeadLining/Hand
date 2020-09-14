use hzero_platform;
UPDATE hbt SET hbt.tenant_id = hb.tenant_id
FROM hpfm_bank hb, hpfm_bank_tl hbt
WHERE hb.bank_id = hbt.bank_id;

UPDATE hchd SET hchd.tenant_id = hc.tenant_id
FROM hpfm_calendar hc, hpfm_calendar_hldy_detail hchd
WHERE hc.calendar_id = hchd.calendar_id;

UPDATE hcht SET hcht.tenant_id = hch.tenant_id
FROM hpfm_calendar_holiday hch, hpfm_calendar_holiday_tl hcht
WHERE hch.holiday_id = hcht.holiday_id;

UPDATE hioe SET hioe.tenant_id = hio.tenant_id
FROM hpfm_inv_organization hio, hpfm_inv_organization_es hioe
WHERE hio.organization_id = hioe.organization_id;

UPDATE hie SET hie.tenant_id = hi.tenant_id
FROM hpfm_inventory hi, hpfm_inventory_es hie
WHERE hi.inventory_id = hie.inventory_id;

UPDATE hlt SET hlt.tenant_id = hl.tenant_id
FROM hpfm_lov hl, hpfm_lov_tl hlt
WHERE hl.lov_id = hlt.lov_id;

UPDATE hlvt SET hlvt.tenant_id = hlv.tenant_id
FROM hpfm_lov_value hlv, hpfm_lov_value_tl hlvt
WHERE hlv.lov_value_id = hlvt.lov_value_id;

UPDATE houe SET houe.tenant_id = hou.tenant_id
FROM hpfm_operation_unit hou, hpfm_operation_unit_es houe
WHERE hou.ou_id = houe.ou_id;

UPDATE hpst SET hpst.tenant_id = hps.tenant_id
FROM hpfm_period_set hps, hpfm_period_set_tl hpst
WHERE hps.period_set_id = hpst.period_set_id;

UPDATE hpt SET hpt.tenant_id = hp.tenant_id
FROM hpfm_position hp, hpfm_position_tl hpt
WHERE hp.position_id = hpt.position_id;

UPDATE hpae SET hpae.tenant_id = hpa.tenant_id
FROM hpfm_purchase_agent hpa, hpfm_pur_agent_es hpae
WHERE hpa.purchase_agent_id = hpae.pur_agent_id;

UPDATE hpoe SET hpoe.tenant_id = hpo.tenant_id
FROM hpfm_purchase_organization hpo, hpfm_pur_organization_es hpoe
WHERE hpo.purchase_org_id = hpoe.purchase_org_id;

UPDATE hpau SET hpau.tenant_id = hpa.tenant_id
FROM hpfm_purchase_agent hpa, hpfm_purchase_agent_user hpau
WHERE hpa.purchase_agent_id = hpau.purchase_agent_id;

UPDATE hrt SET hrt.tenant_id = hr.tenant_id
FROM hpfm_region hr, hpfm_region_tl hrt
WHERE hr.region_id = hrt.region_id;

UPDATE hstv SET hstv.tenant_id = hst.tenant_id
FROM hpfm_static_text hst, hpfm_static_text_value hstv
WHERE hst.text_id = hstv.text_id;

UPDATE htt SET htt.tenant_id = ht.tenant_id
FROM hpfm_tax ht, hpfm_tax_tl htt
WHERE ht.tax_id = htt.tax_id;

UPDATE hut SET hut.tenant_id = hu.tenant_id
FROM hpfm_unit hu, hpfm_unit_tl hut
WHERE hu.unit_id = hut.unit_id;

UPDATE hut SET hut.tenant_id = hu.tenant_id
FROM hpfm_uom hu, hpfm_uom_tl hut
WHERE hu.uom_id = hut.uom_id;

UPDATE hutt SET hutt.tenant_id = hut.tenant_id
FROM hpfm_uom_type hut, hpfm_uom_type_tl hutt
WHERE hut.uom_type_id = hutt.uom_type_id;

UPDATE hdht SET hdht.tenant_id = hdh.tenant_id
FROM hpfm_data_hierarchy hdh, hpfm_data_hierarchy_tl hdht
WHERE hdh.data_hierarchy_id = hdht.data_hierarchy_id;

UPDATE hertt SET hertt.tenant_id = hert.tenant_id
FROM hpfm_exchange_rate_type hert, hpfm_exchange_rate_type_tl hertt
WHERE hert.rate_type_id = hertt.rate_type_id;

UPDATE hict SET hict.tenant_id = hic.tenant_id
FROM hpfm_industry_category hic, hpfm_industry_category_tl hict
WHERE hic.category_id = hict.category_id;

UPDATE hit SET hit.tenant_id = hi.tenant_id
FROM hpfm_industry hi, hpfm_industry_tl hit
WHERE hi.industry_id = hit.industry_id;

UPDATE hile SET hile.tenant_id = hl.tenant_id
FROM hpfm_location hl, hpfm_inv_location_es hile
WHERE hl.location_id = hile.location_id;

UPDATE hit SET hit.tenant_id = hi.tenant_id
FROM hpfm_lov_view_header hi, hpfm_lov_view_header_tl hit
WHERE hi.view_header_id = hit.view_header_id;

UPDATE hile SET hile.tenant_id = hl.tenant_id
FROM hpfm_lov_view_line hl, hpfm_lov_view_line_tl hile
WHERE hl.view_line_id = hile.view_line_id;
