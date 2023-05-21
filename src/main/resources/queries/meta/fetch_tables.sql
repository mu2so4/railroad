SELECT
    table_name,
    table_type,
    is_insertable_into
FROM information_schema.tables
WHERE
    table_schema='public';