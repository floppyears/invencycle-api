package edu.oregonstate.mist.invencycle.db

import edu.oregonstate.mist.invencycle.core.Bike
import edu.oregonstate.mist.invencycle.mapper.BikeMapper
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind

@RegisterMapper(BikeMapper)
public interface BikeDAO extends Closeable {

    /**
     * POST a bike
     */
    @SqlUpdate("""
              INSERT INTO BRAKE (BRAKE_ID, BRAKE_MAKE, BRAKE_MODEL, BRAKE_ROTOR_SIZE, BRAKE_POSITION)
              VALUES (BIKE_BRAKE_ID_SEQ.nextval, :brake_make_front, :brake_model_front, :brake_rotor_size_front, 'Front')
               """)
    void postBrakeFront(@Bind("brake_make_front") String brakeMakeFront,
                        @Bind("brake_model_front") String brakeModelFront,
                        @Bind("brake_rotor_size_front") Integer brakeRotorSizeFront)

    @SqlUpdate("""
              INSERT INTO BRAKE (BRAKE_ID, BRAKE_MAKE, BRAKE_MODEL, BRAKE_ROTOR_SIZE, BRAKE_POSITION)
              VALUES (BIKE_BRAKE_ID_SEQ.nextval, :brake_make_rear, :brake_model_rear, :brake_rotor_size_rear, 'Rear')
               """)
    void postBrakeRear(@Bind("brake_make_rear") String brakeMakeRear,
                       @Bind("brake_model_rear") String brakeModelYear,
                       @Bind("brake_rotor_size_rear") Integer brakeRotorSizeRear)

    @SqlUpdate("""
              INSERT INTO DERAILUER (DERAILUER_ID, DERAILUER_MAKE, DERAILUER_MODEL, DERAILUER_SPEEDS, DERAILUER_POSITION)
              VALUES (BIKE_DERAILUER_ID_SEQ.nextval, :derailuer_make_front, :derailuer_model_front, :derailuer_speeds_front, 'Front')
              """)
    void postDerailuerFront(@Bind("derailuer_make_front") String derailuerMakeFront,
                            @Bind("derailuer_model_front") String derailuerModelFront,
                            @Bind("derailuer_speeds_front") Integer derailuerSpeedsFront)

    @SqlUpdate("""
              INSERT INTO DERAILUER (DERAILUER_ID, DERAILUER_MAKE, DERAILUER_MODEL, DERAILUER_SPEEDS, DERAILUER_POSITION)
              VALUES (BIKE_DERAILUER_ID_SEQ.nextval, :derailuer_make_rear, :derailuer_model_rear, :derailuer_speeds_rear, 'Rear')
              """)
    void postDerailuerRear(@Bind("derailuer_make_rear") String derailuerMakeRear,
                           @Bind("derailuer_model_rear") String derailuerModelRear,
                           @Bind("derailuer_speeds_rear") Integer derailuerSpeedsRear)
    
    @SqlUpdate("""
              INSERT INTO FRAME_SIZE (FRAME_SIZE_ID, FRAME_SIZE_NAME, FRAME_SIZE_CM)
              VALUES (BIKE_FRAME_SIZE_SEQ.nextval, :frame_size_name, :frame_size_cm)
               """)
    void postFrameSize(@Bind("frame_size_name") String frameSizeName,
                       @Bind("frame_size_cm") Integer frameSizeCm)
    
    @SqlUpdate("""
              INSERT INTO SUSPENSION (SUSPENSION_ID, SUSPENSION_MAKE, SUSPENSION_MODEL, SUSPENSION_TRAVEL, SUSPENSION_TYPE)
              VALUES (BIKE_SUSPENSION_ID_SEQ.nextval, :fork_make, :fork_model, :fork_travel, 'Fork')
               """)
    void postFork(@Bind("fork_make") String forkMake,
                  @Bind("fork_model") String forkModel,
                  @Bind("fork_travel") Integer forkTravel)

    @SqlUpdate("""
              INSERT INTO SUSPENSION (SUSPENSION_ID, SUSPENSION_MAKE, SUSPENSION_MODEL, SUSPENSION_TRAVEL, SUSPENSION_TYPE)
              VALUES (BIKE_SUSPENSION_ID_SEQ.nextval, :shock_make, :shock_model, :shock_travel, 'Shock')
               """)
    void postShock(@Bind("shock_make") String shockMake,
                   @Bind("shock_model") String shockModel,
                   @Bind("shock_travel") Integer shockTravel)
    
    @SqlUpdate("""
               INSERT INTO WHEEL (WHEEL_ID, WHEEL_SIZE, WHEEL_RIM_MAKE, WHEEL_RIM_MODEL, WHEEL_HUB_MAKE, WHEEL_HUB_MODEL, WHEEL_POSITION)
               VALUES (BIKE_WHEEL_ID_SEQ.nextval, :wheel_size_front, :rim_make_front, :rim_model_front, :hub_make_front, :hub_model_front, 'Front')
              """)
    void postWheelFront(@Bind("wheel_size_front") Integer wheelSizeFront,
                        @Bind("rim_make_front") String rimMakeFront,
                        @Bind("rim_model_front") String rimModelFront,
                        @Bind("hub_make_front") String hubMakeFront,
                        @Bind("hub_model_front") String hubModelFront)

    @SqlUpdate("""
               INSERT INTO WHEEL (WHEEL_ID, WHEEL_SIZE, WHEEL_RIM_MAKE, WHEEL_RIM_MODEL, WHEEL_HUB_MAKE, WHEEL_HUB_MODEL, WHEEL_POSITION)
               VALUES (BIKE_WHEEL_ID_SEQ.nextval, :wheel_size_rear, :rim_make_rear, :rim_model_rear, :hub_make_rear, :hub_model_rear, 'Rear')
              """)
    void postWheelRear(@Bind("wheel_size_rear") Integer wheelSizeRear,
                       @Bind("rim_make_rear") String rimMakeRear,
                       @Bind("rim_model_rear") String rimModelRear,
                       @Bind("hub_make_rear") String hubMakeRear,
                       @Bind("hub_model_rear") String hubModelRear)

    @SqlUpdate("""
              INSERT INTO BIKE (BIKE_ID, BIKE_MAKE, BIKE_MODEL, BIKE_YEAR, BIKE_BIKE_TYPE_ID, BIKE_FRAME_SIZE_ID, BIKE_MSRP,
                BIKE_DERAILUER_FRONT_ID, BIKE_DERAILUER_REAR_ID, BIKE_FORK_ID, BIKE_SHOCK_ID, BIKE_WHEEL_FRONT_ID,
                BIKE_WHEEL_REAR_ID, BIKE_BRAKE_FRONT_ID, BIKE_BRAKE_REAR_ID, BIKE_CREATE_DATE)
              VALUES (BIKE_ID_SEQ.nextval, :bike_make, :bike_model, :bike_year,

                (SELECT BIKE_TYPE_ID FROM BIKE_TYPE
                WHERE BIKE_TYPE_NAME LIKE :bike_type),

                (SELECT MAX(FRAME_SIZE_ID) FROM FRAME_SIZE WHERE (FRAME_SIZE_NAME LIKE :frame_size_name) OR (FRAME_SIZE_CM LIKE :frame_size_cm)),

              :bike_msrp,

                (SELECT MAX(DERAILUER_ID) FROM DERAILUER WHERE (DERAILUER_MAKE LIKE :derailuer_make_front)
                    AND (DERAILUER_MODEL LIKE :derailuer_model_front)
                    AND (DERAILUER_SPEEDS LIKE :derailuer_speeds_front)
                    AND (DERAILUER_POSITION LIKE 'Front')),

                (SELECT MAX(DERAILUER_ID) FROM DERAILUER WHERE (DERAILUER_MAKE LIKE :derailuer_make_rear)
                    AND (DERAILUER_MODEL LIKE :derailuer_model_rear)
                    AND (DERAILUER_SPEEDS LIKE :derailuer_speeds_rear)
                    AND (DERAILUER_POSITION LIKE 'Rear')),

                (SELECT MAX(SUSPENSION_ID) FROM SUSPENSION WHERE (SUSPENSION_MAKE LIKE :fork_make)
                    AND (SUSPENSION_MODEL LIKE :fork_model)
                    AND (SUSPENSION_TRAVEL LIKE :fork_travel)
                    AND (SUSPENSION_TYPE LIKE 'Fork')),

                (SELECT MAX(SUSPENSION_ID) FROM SUSPENSION WHERE (SUSPENSION_MAKE LIKE :shock_make)
                    AND (SUSPENSION_MODEL LIKE :shock_model)
                    AND (SUSPENSION_TRAVEL LIKE :shock_travel)
                    AND (SUSPENSION_TYPE LIKE 'Shock')),

                (SELECT MAX(WHEEL_ID) FROM WHEEL WHERE (WHEEL_SIZE LIKE :wheel_size_front)
                    AND (WHEEL_RIM_MAKE LIKE :rim_make_front)
                    AND (WHEEL_RIM_MODEL LIKE :rim_model_front)
                    AND (WHEEL_HUB_MAKE LIKE :hub_make_front)
                    AND (WHEEL_HUB_MODEL LIKE :hub_model_front)
                    AND (WHEEL_POSITION LIKE 'Front')),

                (SELECT MAX(WHEEL_ID) FROM WHEEL WHERE (WHEEL_SIZE LIKE :wheel_size_rear)
                    AND (WHEEL_RIM_MAKE LIKE :rim_make_rear)
                    AND (WHEEL_RIM_MODEL LIKE :rim_model_rear)
                    AND (WHEEL_HUB_MAKE LIKE :hub_make_rear)
                    AND (WHEEL_HUB_MODEL LIKE :hub_model_rear)
                    AND (WHEEL_POSITION LIKE 'Rear')),

                (SELECT MAX(BRAKE_ID) FROM BRAKE WHERE (BRAKE_MAKE LIKE :brake_make_front)
                    AND (BRAKE_MODEL LIKE :brake_model_front)
                    AND (BRAKE_ROTOR_SIZE LIKE :brake_rotor_size_front)
                    AND (BRAKE_POSITION LIKE 'Front')),

                (SELECT MAX(BRAKE_ID) FROM BRAKE WHERE (BRAKE_MAKE LIKE :brake_make_rear)
                    AND (BRAKE_MODEL LIKE :brake_model_rear)
                    AND (BRAKE_ROTOR_SIZE LIKE :brake_rotor_size_rear)
                    AND (BRAKE_POSITION LIKE 'Rear')),

              SYSDATE)
              """)
    void postBike(@Bind("bike_make") String make,
                  @Bind("bike_model") String model,
                  @Bind("bike_msrp") Integer msrp,
                  @Bind("bike_year") Integer year,
                  @Bind("bike_type") String type,
                  @Bind("frame_size_name") String frameSizeName,
                  @Bind("frame_size_cm") Integer frameSizeCm,
                  @Bind("derailuer_make_front") String derailuerMakeFront,
                  @Bind("derailuer_model_front") String derailuerModelFront,
                  @Bind("derailuer_speeds_front") Integer derailuerSpeedsFront,
                  @Bind("derailuer_make_rear") String derailuerMakeRear,
                  @Bind("derailuer_model_rear") String derailuerModelRear,
                  @Bind("derailuer_speeds_rear") Integer derailuerSpeedsRear,
                  @Bind("fork_make") String forkMake,
                  @Bind("fork_model") String forkModel,
                  @Bind("fork_travel") Integer forkTravel,
                  @Bind("shock_make") String shockMake,
                  @Bind("shock_model") String shockModel,
                  @Bind("shock_travel") Integer shockTravel,
                  @Bind("wheel_size_front") Integer wheelSizeFront,
                  @Bind("rim_make_front") String rimMakeFront,
                  @Bind("rim_model_front") String rimModelFront,
                  @Bind("hub_make_front") String hubMakeFront,
                  @Bind("hub_model_front") String hubModelFront,
                  @Bind("wheel_size_rear") Integer wheelSizeRear,
                  @Bind("rim_make_rear") String rimMakeRear,
                  @Bind("rim_model_rear") String rimModelRear,
                  @Bind("hub_make_rear") String hubMakeRear,
                  @Bind("hub_model_rear") String hubModelRear,
                  @Bind("brake_make_front") String brakeMakeFront,
                  @Bind("brake_model_front") String brakeModelFront,
                  @Bind("brake_rotor_size_front") Integer brakeRotorSizeFront,
                  @Bind("brake_make_rear") String brakeMakeRear,
                  @Bind("brake_model_rear") String brakeModelYear,
                  @Bind("brake_rotor_size_rear") Integer brakeRotorSizeRear)

    /**
     * PUT to update a bike
     */
    @SqlUpdate("""
        UPDATE BIKE
            SET BIKE_MAKE=:make, BIKE_MODEL=:model, BIKE_YEAR=:year, BIKE_MSRP=:msrp,
            BIKE_BIKE_TYPE_ID=(
                SELECT BIKE_TYPE_ID FROM BIKE_TYPE
                WHERE BIKE_TYPE_NAME LIKE :bike_type)
        WHERE BIKE_ID=:id
        """)
    void updateBike(@Bind("id") Integer id,
                    @Bind("make") String make,
                    @Bind("model") String model,
                    @Bind("year") Integer year,
                    @Bind("msrp") Integer msrp,
                    @Bind("bike_type") String bike_type)
    @SqlUpdate("""
        UPDATE BRAKE
            SET BRAKE_MAKE=:brake_make, BRAKE_MODEL=:brake_model, BRAKE_ROTOR_SIZE=:rotor_size
            WHERE BRAKE_ID=(
                SELECT BIKE_BRAKE_FRONT_ID
                    FROM BIKE
                    WHERE BIKE_ID = :id)
        """)
    void updateBrakeFront(@Bind("id") Integer id,
                          @Bind("brake_make") String brakeMake,
                          @Bind("brake_model") String brakeModel,
                          @Bind("rotor_size") Integer rotorSize)
    @SqlUpdate("""
        UPDATE BRAKE
            SET BRAKE_MAKE=:brake_make, BRAKE_MODEL=:brake_model, BRAKE_ROTOR_SIZE=:rotor_size
            WHERE BRAKE_ID=(
                SELECT BIKE_BRAKE_REAR_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateBrakeRear(@Bind("id") Integer id,
                         @Bind("brake_make") String brakeMake,
                         @Bind("brake_model") String brakeModel,
                         @Bind("rotor_size") Integer rotorSize)
    @SqlUpdate("""
        UPDATE DERAILUER
            SET DERAILUER_MAKE=:derailuer_make, DERAILUER_MODEL=:derailuer_model, DERAILUER_SPEEDS=:derailuer_speeds
            WHERE DERAILUER_ID=(
                SELECT BIKE_DERAILUER_FRONT_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateDerailuerFront(@Bind("id") Integer id,
                              @Bind("derailuer_make") String derailuerMake,
                              @Bind("derailuer_model") String derailuerModel,
                              @Bind("derailuer_speeds") Integer derailuerSpeeds)
    @SqlUpdate("""
        UPDATE DERAILUER
            SET DERAILUER_MAKE=:derailuer_make, DERAILUER_MODEL=:derailuer_model, DERAILUER_SPEEDS=:derailuer_speeds
            WHERE DERAILUER_ID=(
                SELECT BIKE_DERAILUER_REAR_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateDerailuerRear(@Bind("id") Integer id,
                             @Bind("derailuer_make") String derailuerMake,
                             @Bind("derailuer_model") String derailuerModel,
                             @Bind("derailuer_speeds") Integer derailuerSpeeds)
    @SqlUpdate("""
        UPDATE SUSPENSION
            SET SUSPENSION_MAKE=:fork_make, SUSPENSION_MODEL=:fork_model, SUSPENSION_TRAVEL=:fork_travel
            WHERE SUSPENSION_ID=(
                SELECT BIKE_FORK_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateFork(@Bind("id") Integer id,
                    @Bind("fork_make") String forkMake,
                    @Bind("fork_model") String forkModel,
                    @Bind("fork_travel") Integer forkTravel)
    @SqlUpdate("""
        UPDATE SUSPENSION
            SET SUSPENSION_MAKE=:shock_make, SUSPENSION_MODEL=:shock_model, SUSPENSION_TRAVEL=:shock_travel
            WHERE SUSPENSION_ID=(
                SELECT BIKE_SHOCK_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateShock(@Bind("id") Integer id,
                     @Bind("shock_make") String shockMake,
                     @Bind("shock_model") String shockModel,
                     @Bind("shock_travel") Integer shockTravel)
    @SqlUpdate("""
        UPDATE WHEEL
            SET WHEEL_SIZE=:wheel_size, WHEEL_RIM_MAKE=:rim_make, WHEEL_RIM_MODEL=:rim_model,
                WHEEL_HUB_MAKE=:hub_make, WHEEL_HUB_MODEL=:hub_model
            WHERE WHEEL_ID=(
                SELECT BIKE_WHEEL_FRONT_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateWheelFront(@Bind("id") Integer id,
                          @Bind("wheel_size") Integer wheelSize,
                          @Bind("rim_make") String rimMake,
                          @Bind("rim_model") String rimModel,
                          @Bind("hub_make") String hubMake,
                          @Bind("hub_model") String hubModel)
    @SqlUpdate("""
        UPDATE WHEEL
            SET WHEEL_SIZE=:wheel_size, WHEEL_RIM_MAKE=:rim_make, WHEEL_RIM_MODEL=:rim_model,
                WHEEL_HUB_MAKE=:hub_make, WHEEL_HUB_MODEL=:hub_model
            WHERE WHEEL_ID=(
                SELECT BIKE_WHEEL_REAR_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateWheelRear(@Bind("id") Integer id,
                         @Bind("wheel_size") Integer wheelSize,
                         @Bind("rim_make") String rimMake,
                         @Bind("rim_model") String rimModel,
                         @Bind("hub_make") String hubMake,
                         @Bind("hub_model") String hubModel)
    @SqlUpdate("""
        UPDATE FRAME_SIZE
            SET FRAME_SIZE_NAME=:frame_size_name, FRAME_SIZE_CM=:frame_size_cm
            WHERE FRAME_SIZE_ID=(
                SELECT BIKE_FRAME_SIZE_ID
                    FROM BIKE
                    WHERE BIKE_ID=:id)
        """)
    void updateFrameSize(@Bind("id") Integer id,
                         @Bind("frame_size_name") String frameSizeName,
                         @Bind("frame_size_cm") Integer frameSizeCm)

    /**
     * Delete by ID
     */
    @SqlUpdate("""
        DELETE FROM BIKE
        WHERE BIKE.BIKE_ID = :id
        """)
    void deleteById(@Bind("id") Integer id)

    /**
     * Get by query
     */
    @SqlQuery("""
          SELECT
              BIKE.BIKE_ID,
              BIKE.BIKE_MAKE,
              BIKE.BIKE_MODEL,
              BIKE.BIKE_YEAR,
              BIKE_TYPE.BIKE_TYPE_NAME,
              FRAME_SIZE.FRAME_SIZE_NAME,
              FRAME_SIZE.FRAME_SIZE_CM,
              BIKE.BIKE_MSRP,
              D1.DERAILUER_MAKE AS DERAILUER_MAKE_FRONT,
              D1.DERAILUER_MODEL AS DERAILUER_MODEL_FRONT,
              D1.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_FRONT,
              D2.DERAILUER_MAKE AS DERAILUER_MAKE_REAR,
              D2.DERAILUER_MODEL AS DERAILUER_MODEL_REAR,
              D2.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_REAR,
              F.SUSPENSION_MAKE AS FORK_MAKE,
              F.SUSPENSION_MODEL AS FORK_MODEL,
              F.SUSPENSION_TRAVEL AS FORK_TRAVEL,
              S.SUSPENSION_MAKE AS SHOCK_MAKE,
              S.SUSPENSION_MODEL AS SHOCK_MODEL,
              S.SUSPENSION_TRAVEL AS SHOCK_TRAVEL,
              WF.WHEEL_SIZE AS WHEEL_SIZE_FRONT,
              WF.WHEEL_RIM_MAKE AS RIM_MAKE_FRONT,
              WF.WHEEL_RIM_MODEL AS RIM_MODEL_FRONT,
              WF.WHEEL_HUB_MAKE AS HUB_MAKE_FRONT,
              WF.WHEEL_HUB_MODEL AS HUB_MODEL_FRONT,
              WR.WHEEL_SIZE AS WHEEL_SIZE_REAR,
              WR.WHEEL_RIM_MAKE AS RIM_MAKE_REAR,
              WR.WHEEL_RIM_MODEL AS RIM_MODEL_REAR,
              WR.WHEEL_HUB_MAKE AS HUB_MAKE_REAR,
              WR.WHEEL_HUB_MODEL AS HUB_MODEL_REAR,
              BF.BRAKE_MAKE AS BRAKE_MAKE_FRONT,
              BF.BRAKE_MODEL AS BRAKE_MODEL_FRONT,
              BF.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_FRONT,
              BR.BRAKE_MAKE AS BRAKE_MAKE_REAR,
              BR.BRAKE_MODEL AS BRAKE_MODEL_REAR,
              BR.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_REAR,
              BIKE.BIKE_CREATE_DATE,
              BIKE.BIKE_DELETE_DATE

        FROM BIKE

        LEFT JOIN BIKE_TYPE
          ON BIKE.BIKE_BIKE_TYPE_ID = BIKE_TYPE.BIKE_TYPE_ID
        LEFT JOIN FRAME_SIZE
          ON BIKE.BIKE_FRAME_SIZE_ID = FRAME_SIZE.FRAME_SIZE_ID
        LEFT JOIN DERAILUER D1
          ON D1.DERAILUER_ID = BIKE.BIKE_DERAILUER_FRONT_ID
        LEFT JOIN DERAILUER D2
          ON D2.DERAILUER_ID = BIKE.BIKE_DERAILUER_REAR_ID
        LEFT JOIN SUSPENSION F
          ON F.SUSPENSION_ID = BIKE.BIKE_FORK_ID
        LEFT JOIN SUSPENSION S
          ON S.SUSPENSION_ID = BIKE.BIKE_SHOCK_ID
        LEFT JOIN WHEEL WF
          ON WF.WHEEL_ID = BIKE.BIKE_WHEEL_FRONT_ID
        LEFT JOIN WHEEL WR
          ON WR.WHEEL_ID = BIKE.BIKE_WHEEL_REAR_ID
        LEFT JOIN BRAKE BF
          ON BF.BRAKE_ID = BIKE.BIKE_BRAKE_FRONT_ID
        LEFT JOIN BRAKE BR
          ON BR.BRAKE_ID = BIKE.BIKE_BRAKE_REAR_ID

        WHERE BIKE.BIKE_MAKE LIKE '%' ||:make|| '%'
        AND BIKE.BIKE_MODEL LIKE '%' ||:model|| '%'
        AND BIKE_TYPE.BIKE_TYPE_NAME LIKE '%' ||:type|| '%'
        """)

    List<Bike> getBikeByQuery(@Bind("make") String make,
                              @Bind("model") String model,
                              @Bind("type") String type)
    /**
     * Get by ID
     */
    @SqlQuery("""
        SELECT
          BIKE.BIKE_ID,
          BIKE.BIKE_MAKE,
          BIKE.BIKE_MODEL,
          BIKE.BIKE_YEAR,
          BIKE_TYPE.BIKE_TYPE_NAME,
          FRAME_SIZE.FRAME_SIZE_NAME,
          FRAME_SIZE.FRAME_SIZE_CM,
          BIKE.BIKE_MSRP,
          D1.DERAILUER_MAKE AS DERAILUER_MAKE_FRONT,
          D1.DERAILUER_MODEL AS DERAILUER_MODEL_FRONT,
          D1.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_FRONT,
          D2.DERAILUER_MAKE AS DERAILUER_MAKE_REAR,
          D2.DERAILUER_MODEL AS DERAILUER_MODEL_REAR,
          D2.DERAILUER_SPEEDS AS DERAILUER_SPEEDS_REAR,
          F.SUSPENSION_MAKE AS FORK_MAKE,
          F.SUSPENSION_MODEL AS FORK_MODEL,
          F.SUSPENSION_TRAVEL AS FORK_TRAVEL,
          S.SUSPENSION_MAKE AS SHOCK_MAKE,
          S.SUSPENSION_MODEL AS SHOCK_MODEL,
          S.SUSPENSION_TRAVEL AS SHOCK_TRAVEL,
          WF.WHEEL_SIZE AS WHEEL_SIZE_FRONT,
          WF.WHEEL_RIM_MAKE AS RIM_MAKE_FRONT,
          WF.WHEEL_RIM_MODEL AS RIM_MODEL_FRONT,
          WF.WHEEL_HUB_MAKE AS HUB_MAKE_FRONT,
          WF.WHEEL_HUB_MODEL AS HUB_MODEL_FRONT,
          WR.WHEEL_SIZE AS WHEEL_SIZE_REAR,
          WR.WHEEL_RIM_MAKE AS RIM_MAKE_REAR,
          WR.WHEEL_RIM_MODEL AS RIM_MODEL_REAR,
          WR.WHEEL_HUB_MAKE AS HUB_MAKE_REAR,
          WR.WHEEL_HUB_MODEL AS HUB_MODEL_REAR,
          BF.BRAKE_MAKE AS BRAKE_MAKE_FRONT,
          BF.BRAKE_MODEL AS BRAKE_MODEL_FRONT,
          BF.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_FRONT,
          BR.BRAKE_MAKE AS BRAKE_MAKE_REAR,
          BR.BRAKE_MODEL AS BRAKE_MODEL_REAR,
          BR.BRAKE_ROTOR_SIZE AS BRAKE_ROTOR_SIZE_REAR,
          BIKE.BIKE_CREATE_DATE,
          BIKE.BIKE_DELETE_DATE

        FROM BIKE

        LEFT JOIN BIKE_TYPE
          ON BIKE.BIKE_BIKE_TYPE_ID = BIKE_TYPE.BIKE_TYPE_ID
        LEFT JOIN FRAME_SIZE
          ON BIKE.BIKE_FRAME_SIZE_ID = FRAME_SIZE.FRAME_SIZE_ID
        LEFT JOIN DERAILUER D1
          ON D1.DERAILUER_ID = BIKE.BIKE_DERAILUER_FRONT_ID
        LEFT JOIN DERAILUER D2
          ON D2.DERAILUER_ID = BIKE.BIKE_DERAILUER_REAR_ID
        LEFT JOIN SUSPENSION F
          ON F.SUSPENSION_ID = BIKE.BIKE_FORK_ID
        LEFT JOIN SUSPENSION S
          ON S.SUSPENSION_ID = BIKE.BIKE_SHOCK_ID
        LEFT JOIN WHEEL WF
          ON WF.WHEEL_ID = BIKE.BIKE_WHEEL_FRONT_ID
        LEFT JOIN WHEEL WR
          ON WR.WHEEL_ID = BIKE.BIKE_WHEEL_REAR_ID
        LEFT JOIN BRAKE BF
          ON BF.BRAKE_ID = BIKE.BIKE_BRAKE_FRONT_ID
        LEFT JOIN BRAKE BR
          ON BR.BRAKE_ID = BIKE.BIKE_BRAKE_REAR_ID
        WHERE BIKE.BIKE_ID = :id
        """)
    Bike getById(@Bind("id") Integer id)

    /**
     * Check if ID exists for PUT bike
     */
    @SqlQuery("""
        SELECT BIKE_ID from BIKE
            WHERE BIKE_ID = :id
        """)
    Integer idCheck(@Bind("id") Integer id)

    @Override
    void close()

}
