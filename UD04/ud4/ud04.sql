CREATE TABLE Conductor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    vehiculo VARCHAR(255) NOT NULL
);

CREATE TABLE Viaje (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ciudadDestino VARCHAR(255) NOT NULL,
    ciudadOrigen VARCHAR(255) NOT NULL,
    fechaHora DATETIME(6) NOT NULL,
    plazasDisponibles INT NOT NULL,
    conductor BIGINT NOT NULL,
    CONSTRAINT FK_Viaje_Conductor FOREIGN KEY (conductor) REFERENCES Conductor(id)
);

CREATE TABLE Pasajero (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE Reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fechaReserva DATE NOT NULL,
    numeroPlazasReservadas INT NOT NULL,
    pasajero_id BIGINT NOT NULL,
    viaje_id BIGINT NOT NULL,
    CONSTRAINT FK_Reserva_Pasajero FOREIGN KEY (pasajero_id) REFERENCES Pasajero(id),
    CONSTRAINT FK_Reserva_Viaje FOREIGN KEY (viaje_id) REFERENCES Viaje(id)
);
