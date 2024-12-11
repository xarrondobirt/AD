DELIMITER //

CREATE DEFINER=`root`@`localhost` FUNCTION `obtener_numero_asistentes`(idEvento INT) RETURNS INT
    DETERMINISTIC
BEGIN
  DECLARE aforo INT;
  
 -- Comprobar si el idEvento existe
  IF NOT EXISTS (
      SELECT 1
      FROM eventos
      WHERE id_evento = idEvento
  ) THEN
      SIGNAL SQLSTATE '45000' 
      SET MESSAGE_TEXT = 'El evento no existe';
  END IF;

  SELECT COUNT(a.dni) INTO aforo
  FROM eventos e
  LEFT JOIN asistentes_eventos a ON e.id_evento = a.id_evento
  WHERE e.id_evento = idEvento;
  RETURN aforo;
END //

DELIMITER ;
