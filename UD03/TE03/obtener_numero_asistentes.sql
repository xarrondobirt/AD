CREATE DEFINER=`root`@`localhost` FUNCTION `obtener_numero_asistentes`(idEvento INT) RETURNS int
    DETERMINISTIC
BEGIN
  DECLARE aforo INT;
  SELECT COUNT(a.dni) INTO aforo
  FROM eventos e
  LEFT JOIN asistentes_eventos a ON e.id_evento = a.id_evento
  WHERE e.id_evento = idEvento;
  RETURN aforo;
END