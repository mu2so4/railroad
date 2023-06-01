WITH "primary_keys" AS (
    SELECT
        relname,
        conkey
    FROM pg_catalog.pg_constraint con
        INNER JOIN pg_catalog.pg_class rel
            ON rel.oid = con.conrelid
        INNER JOIN pg_catalog.pg_namespace nsp
            ON nsp.oid = connamespace
    WHERE
        nsp.nspname='public' AND
        contype = 'p'
)

SELECT
    table_name,
    table_type,
    is_insertable_into,
    conkey AS primary_key
FROM information_schema.tables tab
    LEFT OUTER JOIN "primary_keys" pks
        ON tab.table_name = pks.relname
WHERE
    table_schema='public';