SELECT
	column_name,
	is_nullable,
	udt_name,
	character_maximum_length,
	is_updatable,
	ordinal_position
FROM information_schema.columns
WHERE
	table_schema = 'public' AND
	table_name = ?
ORDER BY
    ordinal_position;