use hzero_platform;
UPDATE hpfm_bank_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_bank hb WHERE hb.bank_id = hbt.bank_id)
WHERE hbt.bank_id IN (SELECT bank_id FROM hpfm_bank);

UPDATE hpfm_calendar_hldy_detail hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_calendar hb WHERE hb.calendar_id = hbt.calendar_id )
WHERE hbt.calendar_id IN (SELECT calendar_id FROM hpfm_calendar);

UPDATE hpfm_calendar_holiday_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_calendar_holiday hb WHERE hb.holiday_id = hbt.holiday_id )
WHERE hbt.holiday_id IN (SELECT holiday_id FROM hpfm_calendar_holiday);

UPDATE hpfm_inv_organization_es hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_inv_organization hb WHERE hb.organization_id = hbt.organization_id )
WHERE hbt.organization_id IN (SELECT organization_id FROM hpfm_inv_organization);

UPDATE hpfm_inventory_es hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_inventory hb WHERE hb.inventory_id = hbt.inventory_id )
WHERE hbt.inventory_id IN (SELECT inventory_id FROM hpfm_inventory);

UPDATE hpfm_lov_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_lov hb WHERE hb.lov_id = hbt.lov_id )
WHERE hbt.lov_id IN (SELECT lov_id FROM hpfm_lov);


UPDATE hpfm_lov_value_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_lov_value hb WHERE hb.lov_value_id = hbt.lov_value_id )
WHERE hbt.lov_value_id IN (SELECT lov_value_id FROM hpfm_lov_value);

UPDATE hpfm_operation_unit_es hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_operation_unit hb WHERE hb.ou_id = hbt.ou_id )
WHERE hbt.ou_id IN (SELECT ou_id FROM hpfm_operation_unit);

UPDATE hpfm_period_set_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_period_set hb WHERE hb.period_set_id = hbt.period_set_id )
WHERE hbt.period_set_id IN (SELECT period_set_id FROM hpfm_period_set);

UPDATE hpfm_position_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_position hb WHERE hb.position_id = hbt.position_id )
WHERE hbt.position_id IN (SELECT position_id FROM hpfm_position);

UPDATE hpfm_pur_agent_es hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_purchase_agent hb WHERE hb.purchase_agent_id = hbt.pur_agent_id )
WHERE hbt.pur_agent_id IN (SELECT purchase_agent_id FROM hpfm_purchase_agent);

UPDATE hpfm_pur_organization_es hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_purchase_organization hb WHERE hb.purchase_org_id = hbt.purchase_org_id )
WHERE hbt.purchase_org_id IN (SELECT purchase_org_id FROM hpfm_purchase_organization);

UPDATE hpfm_purchase_agent_user hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_purchase_agent hb WHERE hb.purchase_agent_id = hbt.purchase_agent_id )
WHERE hbt.purchase_agent_id IN (SELECT purchase_agent_id FROM hpfm_purchase_agent);

UPDATE hpfm_region_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_region hb WHERE hb.region_id = hbt.region_id )
WHERE hbt.region_id IN (SELECT region_id FROM hpfm_region);

UPDATE hpfm_static_text_value hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_static_text hb WHERE hb.text_id = hbt.text_id )
WHERE hbt.text_id IN (SELECT text_id FROM hpfm_static_text);

UPDATE hpfm_tax_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_tax hb WHERE hb.tax_id = hbt.tax_id )
WHERE hbt.tax_id IN (SELECT tax_id FROM hpfm_tax);

UPDATE hpfm_uom_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_uom hb WHERE hb.uom_id = hbt.uom_id )
WHERE hbt.uom_id IN (SELECT uom_id FROM hpfm_uom);

UPDATE hpfm_uom_type_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_uom_type hb WHERE hb.uom_type_id = hbt.uom_type_id )
WHERE hbt.uom_type_id IN (SELECT uom_type_id FROM hpfm_uom_type);

UPDATE hpfm_data_hierarchy_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_data_hierarchy hb WHERE hb.data_hierarchy_id = hbt.data_hierarchy_id )
WHERE hbt.data_hierarchy_id IN (SELECT data_hierarchy_id FROM hpfm_data_hierarchy);

UPDATE hpfm_exchange_rate_type_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_exchange_rate_type hb WHERE hb.rate_type_id = hbt.rate_type_id )
WHERE hbt.rate_type_id IN (SELECT rate_type_id FROM hpfm_exchange_rate_type);

UPDATE hpfm_industry_category_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_industry_category hb WHERE hb.category_id = hbt.category_id )
WHERE hbt.category_id IN (SELECT category_id FROM hpfm_industry_category);

UPDATE hpfm_industry_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_industry hb WHERE hb.industry_id = hbt.industry_id )
WHERE hbt.industry_id IN (SELECT industry_id FROM hpfm_industry);

UPDATE hpfm_inv_location_es hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_location hb WHERE hb.location_id = hbt.location_id )
WHERE hbt.location_id IN (SELECT location_id FROM hpfm_location);

UPDATE hpfm_unit_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_unit hb WHERE hb.unit_id = hbt.unit_id )
WHERE hbt.unit_id IN (SELECT unit_id FROM hpfm_unit);

UPDATE hpfm_lov_view_header_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_lov_view_header hb WHERE hb.view_header_id = hbt.view_header_id )
WHERE hbt.view_header_id IN (SELECT view_header_id FROM hpfm_lov_view_header);

UPDATE hpfm_lov_view_line_tl hbt
SET hbt.tenant_id = ( SELECT hb.tenant_id FROM hpfm_lov_view_line hb WHERE hb.view_line_id = hbt.view_line_id )
WHERE hbt.view_line_id IN (SELECT view_line_id FROM hpfm_lov_view_line);
