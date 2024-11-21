-- INSERT INTO trucks
INSERT INTO trucks (id, license_plate, model, capacity_limit, current_load, status, is_deleted, created_at, updated_at)
    VALUES ('f7b3b3b4-0b3b-4b3b-8b3b-3b3b3b3b3b3b', 'MW3-123', 'Volvo FH16', 100, 0, 'UNLOADED', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trucks (id, license_plate, model, capacity_limit, current_load, status, is_deleted, created_at, updated_at)
    VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d','YMX-134', 'Volvo FM', 75, 0, 'UNLOADED', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO trucks (id, license_plate, model, capacity_limit, current_load, status, is_deleted, created_at, updated_at)
    VALUES ('123e4567-e89b-12d3-a456-426614174000','PER-150', 'Volvo FMX', 90, 45, 'AVAILABLE', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- loads
INSERT INTO loads (truck_id, volume, description, load_timestamp, unload_timestamp, created_at)
    VALUES ('f7b3b3b4-0b3b-4b3b-8b3b-3b3b3b3b3b3b', 20, 'JBL Partybox 310', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO loads (truck_id, volume, description, load_timestamp, unload_timestamp, created_at)
    VALUES ('f7b3b3b4-0b3b-4b3b-8b3b-3b3b3b3b3b3b', 25, 'Televisor Samsung 50 pulgadas', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);