#OWNER R4
SELECT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor,
	nights.nights, (nights.nights / 365), revs.rev
FROM rooms, reservations,
	(SELECT SUM( IF((YEAR(res1.CheckIn) < 2010 AND YEAR(res1.Checkout) = 2010),
			DATEDIFF(res1.Checkout, '2010-01-01'),
		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) = 2010),
			DATEDIFF(res1.Checkout, res1.CheckIn),
		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) > 2010),
			DATEDIFF('2010-12-31', res1.CheckIn),
            365)))) as nights,
		r1.RoomCode as roc
	FROM rooms as r1, reservations as res1
    WHERE r1.RoomCode = res1.Room AND
		YEAR(res1.CheckIn) <= 2010 AND
		YEAR(res1.Checkout) >= 2010
	GROUP BY r1.RoomCode
    ) as nights,
    
    (SELECT SUM( IF((YEAR(res1.CheckIn) < 2010 AND YEAR(res1.Checkout) = 2010),
			DATEDIFF(res1.Checkout, '2010-01-01') * res1.Rate,
		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) = 2010),
			DATEDIFF(res1.Checkout, res1.CheckIn) * res1.Rate,
		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) > 2010),
			DATEDIFF('2010-12-31', res1.CheckIn) * res1.Rate,
            365 * res1.Rate)))) as rev,
		r1.RoomCode as roc
	FROM rooms as r1, reservations as res1
    WHERE r1.RoomCode = res1.Room AND
		YEAR(res1.CheckIn) <= 2010 AND
		YEAR(res1.Checkout) >= 2010
	GROUP BY r1.RoomCode
    ) as revs
	
WHERE rooms.RoomCode = reservations.Room AND
	rooms.RoomCode = nights.roc AND
    rooms.RoomCode = revs.roc AND
	YEAR(CheckIn) <= 2010 AND
    YEAR(Checkout) >= 2010
GROUP BY RoomCode;

#GUEST R1
SELECT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor, nights.nights, (nights.nights / 365)
FROM rooms, reservations,
	(SELECT SUM( IF((YEAR(res1.CheckIn) < 2010 AND YEAR(res1.Checkout) = 2010),
			DATEDIFF(res1.Checkout, '2010-01-01'),
		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) = 2010),
			DATEDIFF(res1.Checkout, res1.CheckIn),
		IF((YEAR(res1.CheckIn) = 2010 AND YEAR(res1.Checkout) > 2010),
			DATEDIFF('2010-12-31', res1.CheckIn),
            365)))) as nights,
		r1.RoomCode as roc
	FROM rooms as r1, reservations as res1
    WHERE r1.RoomCode = res1.Room AND
		YEAR(res1.CheckIn) <= 2010 AND
		YEAR(res1.Checkout) >= 2010
	GROUP BY r1.RoomCode
    ) as nights
	
WHERE rooms.RoomCode = reservations.Room AND
	rooms.RoomCode = nights.roc AND
	YEAR(CheckIn) <= 2010 AND
    YEAR(Checkout) >= 2010
GROUP BY RoomCode;

#R2, count should be 0 if no reservation, 1 if there is a reservation
SELECT COUNT(*)
FROM rooms, reservations
WHERE rooms.RoomCode = reservations.Room AND
	rooms.RoomCode = 'AOB' AND #PROGRAM INPUT ROOMCODE
	reservations.CheckIn <= '2010-06-23' AND #(PROGRAM INPUT DATE)
    reservations.Checkout >= '2010-06-23' #(PROGRAM INPUT DATE)
;