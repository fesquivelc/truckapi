-- V1__init_schema.sql
--CREATE IF NOT EXISTS EXTENSION "uuid-ossp";
CREATE TABLE IF NOT EXISTS trucks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    license_plate VARCHAR(20) NOT NULL,
    model VARCHAR(100) NOT NULL,
    capacity_limit DOUBLE PRECISION NOT NULL,
    current_load DOUBLE PRECISION NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS loads (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    truck_id UUID NOT NULL,
    volume DOUBLE PRECISION NOT NULL,
    description TEXT NOT NULL,
    load_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    unload_timestamp TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_truck FOREIGN KEY (truck_id) REFERENCES trucks(id)
);

-- indices
CREATE INDEX IF NOT EXISTS idx_trucks_status ON trucks(status);
CREATE INDEX IF NOT EXISTS idx_loads_truck_id ON loads(truck_id);
CREATE INDEX IF NOT EXISTS idx_loads_load_timestamp ON loads(load_timestamp);
CREATE UNIQUE INDEX IF NOT EXISTS idx_trucks_license_plate ON trucks(license_plate) WHERE is_deleted = FALSE;

-- Trigger para updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

DROP TRIGGER IF EXISTS update_trucks_updated_at ON trucks;
CREATE TRIGGER update_trucks_updated_at
    BEFORE UPDATE ON trucks
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

DROP TRIGGER IF EXISTS update_loads_updated_at ON loads;
CREATE TRIGGER update_loads_updated_at
    BEFORE UPDATE ON loads
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();