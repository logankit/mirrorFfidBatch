SELECT *
FROM C2O_API_REQUEST car
WHERE car.REQUEST_STATUS = 318
AND EXISTS (
    SELECT 1
    FROM C2O_CONTRACT cc
    WHERE cc.CONTRACT_ID = car.BASE_CONTRACT_ID
    AND cc.LOCKED_BY IS NULL
    AND cc.VERSION_NUMBER = (
        SELECT MAX(VERSION_NUMBER)
        FROM C2O_CONTRACT
        WHERE ROOT_CONTRACT_ID = cc.ROOT_CONTRACT_ID
    )
)
AND car.CREATED_DATE = (
    SELECT MIN(CREATED_DATE)
    FROM C2O_API_REQUEST
    WHERE BASE_CONTRACT_ID = car.BASE_CONTRACT_ID
    AND REQUEST_STATUS = 318
);
