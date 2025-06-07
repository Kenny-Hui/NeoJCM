package com.lx862.jcm.mod.registry;

import com.lx862.jcm.loader.JCMRegistry;
import com.lx862.jcm.loader.JCMRegistryClient;
import com.lx862.jcm.mod.block.*;
import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.util.JCMLogger;
import mtr.block.IBlock;
import mtr.RegistryObject;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.lx862.jcm.mod.block.SpotLampBlock.LIT;

public final class Blocks {
    public static final RegistryObject<Block> APG_DOOR_DRL = new RegistryObject<>(APGDoorDRL::new);
    public static final RegistryObject<Block> APG_GLASS_DRL = new RegistryObject<>(APGGlassDRL::new);
    public static final RegistryObject<Block> APG_GLASS_END_DRL = new RegistryObject<>(APGGlassEndDRL::new);
    public static final RegistryObject<Block> AUTO_IRON_DOOR = new RegistryObject<>(() -> new AutoIronDoorBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> BUTTERFLY_LIGHT = new RegistryObject<>(() -> new ButterflyLightBlock(BlockBehaviour.Properties.of().strength(3.0f).noOcclusion()));
    public static final RegistryObject<Block> BUFFER_STOP = new RegistryObject<>(() -> new BufferStopBlock(BlockBehaviour.Properties.of().lightLevel(state -> 8).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> CEILING_SLANTED = new RegistryObject<>(() -> new CeilingSlantedBlock(BlockBehaviour.Properties.of().strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> CIRCLE_WALL_1 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f)));
    public static final RegistryObject<Block> CIRCLE_WALL_2 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f)));
    public static final RegistryObject<Block> CIRCLE_WALL_3 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f)));
    public static final RegistryObject<Block> CIRCLE_WALL_4 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f)));
    public static final RegistryObject<Block> CIRCLE_WALL_5 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f)));
    public static final RegistryObject<Block> CIRCLE_WALL_6 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f)));
    public static final RegistryObject<Block> CIRCLE_WALL_7 = new RegistryObject<>(() -> new CircleWallBlock(BlockBehaviour.Properties.of().strength(8.0f).noOcclusion()));
    public static final RegistryObject<Block> DEPARTURE_POLE = new RegistryObject<>(() -> new DeparturePoleBlock(BlockBehaviour.Properties.of().strength(3.0f).noOcclusion()));
    public static final RegistryObject<Block> DEPARTURE_TIMER = new RegistryObject<>(() -> new DepartureTimerBlock(BlockBehaviour.Properties.of().lightLevel(state -> 4).strength(3.0f).noOcclusion()));
    public static final RegistryObject<Block> EXIT_SIGN_ODD = new RegistryObject<>(() -> new ExitSignOdd(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(1.5f).noOcclusion()));
    public static final RegistryObject<Block> EXIT_SIGN_EVEN = new RegistryObject<>(() -> new ExitSignEven(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(1.5f).noOcclusion()));
    public static final RegistryObject<Block> FIRE_ALARM = new RegistryObject<>(() -> new FireAlarmWall(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));
    public static final RegistryObject<Block> FARE_SAVER = new RegistryObject<>(() -> new FareSaverBlock(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> HELPLINE_1 = new RegistryObject<>(() -> new WallAttachedHelpLineBlock(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));
    public static final RegistryObject<Block> HELPLINE_2 = new RegistryObject<>(() -> new WallAttachedHelpLineBlock(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));
    public static final RegistryObject<Block> HELPLINE_HKWK = new RegistryObject<>(() -> new WallAttachedHelpLineBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> HELPLINE_STANDING = new RegistryObject<>(() -> new HelpLineStandingBlock(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> HELPLINE_STANDING_EAL = new RegistryObject<>(() -> new HelpLineStandingEALBlock(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> KCR_EMG_STOP_SIGN = new RegistryObject<>(() -> new KCREmergencyStopSign(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> KCR_ENQUIRY_MACHINE = new RegistryObject<>(() -> new KCREnquiryMachineWall(BlockBehaviour.Properties.of().lightLevel(state -> 4).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> KCR_STATION_NAME_SIGN = new RegistryObject<>(() -> new KCRStationNameSignBlock(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(4.0f).noOcclusion(), false));
    public static final RegistryObject<Block> KCR_STATION_NAME_SIGN_STATION_COLOR = new RegistryObject<>(() -> new KCRStationNameSignBlock(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(4.0f).noOcclusion(), true));
    public static final RegistryObject<Block> KCR_TRESPASS_SIGN = new RegistryObject<>(() -> new KCRTrespassSignageBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> LCD_PIDS = new RegistryObject<>(() -> new LCDPIDSBlock(BlockBehaviour.Properties.of().lightLevel(state -> 8).strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_BLOCK = new RegistryObject<>(() -> new LightBlock(BlockBehaviour.Properties.of().lightLevel(state -> IBlock.getStatePropertySafe(state, BlockProperties.LIGHT_LEVEL)).strength(1.0f).noOcclusion()));
    public static final RegistryObject<Block> LIGHT_LANTERN = new RegistryObject<>(() -> new LightLanternBlock(BlockBehaviour.Properties.of().lightLevel(state -> IBlock.getStatePropertySafe(state, LIT) ? 15 : 0).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> LRT_TRESPASS_SIGN = new RegistryObject<>(() -> new LRTTrespassSignageBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> LRT_INTER_CAR_BARRIER_LEFT = new RegistryObject<>(() -> new LRTInterCarBarrierBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> LRT_INTER_CAR_BARRIER_MIDDLE = new RegistryObject<>(() -> new LRTInterCarBarrierBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> LRT_INTER_CAR_BARRIER_RIGHT = new RegistryObject<>(() -> new LRTInterCarBarrierBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> MTR_ENQUIRY_MACHINE = new RegistryObject<>(() -> new MTREnquiryMachine(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> MTR_ENQUIRY_MACHINE_WALL = new RegistryObject<>(() -> new MTREnquiryMachineWall(BlockBehaviour.Properties.of().lightLevel(state -> 4).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> MTR_STAIRS = new RegistryObject<>(() -> new MTRStairsBlock(BlockBehaviour.Properties.of().strength(4.0f)));
    public static final RegistryObject<Block> MTR_TRESPASS_SIGN = new RegistryObject<>(() -> new MTRTrespassSignageBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> OPERATOR_BUTTON = new RegistryObject<>(() -> new OperatorButtonBlock(BlockBehaviour.Properties.of().lightLevel(state -> 5).strength(1.0f).noOcclusion(), 40));
    public static final RegistryObject<Block> PIDS_PROJECTOR = new RegistryObject<>(() -> new PIDSProjectorBlock(BlockBehaviour.Properties.of().strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> PIDS_1A = new RegistryObject<>(() -> new PIDS1ABlock());
    public static final RegistryObject<Block> RV_PIDS = new RegistryObject<>(() -> new RVPIDSBlock(BlockBehaviour.Properties.of().lightLevel(state -> 8).strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> RV_PIDS_SIL_1 = new RegistryObject<>(() -> new RVPIDSSIL1Block(BlockBehaviour.Properties.of().lightLevel(state -> 8).strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> RV_PIDS_SIL_2 = new RegistryObject<>(() -> new RVPIDSSIL2Block(BlockBehaviour.Properties.of().lightLevel(state -> 8).strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> RV_PIDS_POLE = new RegistryObject<>(() -> new RVPIDSPole(BlockBehaviour.Properties.of().strength(2.0f).noOcclusion()));
    public static final RegistryObject<Block> RV_ENQUIRY_MACHINE = new RegistryObject<>(() -> new RVEnquiryMachine(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> SIL_EMG_STOP_BUTTON = new RegistryObject<>(() -> new SILEmergencyButtonBlock(BlockBehaviour.Properties.of().lightLevel(state -> 10).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> SIGNAL_LIGHT_INVERTED_RED_ABOVE = new RegistryObject<>(() -> new InvertedSignalBlockRedAbove(BlockBehaviour.Properties.of().strength(1.0f)));
    public static final RegistryObject<Block> SIGNAL_LIGHT_INVERTED_RED_BOTTOM = new RegistryObject<>(() -> new InvertedSignalBlockRedBelow(BlockBehaviour.Properties.of().strength(1.0f)));
    public static final RegistryObject<Block> STATIC_SIGNAL_LIGHT_RED_BELOW = new RegistryObject<>(() -> new StaticSignalLightBlockRedBelow(BlockBehaviour.Properties.of().strength(1.0f)));
    public static final RegistryObject<Block> STATIC_SIGNAL_LIGHT_RED_TOP = new RegistryObject<>(() -> new StaticSignalLightBlockRedTop(BlockBehaviour.Properties.of().strength(1.0f)));
    public static final RegistryObject<Block> STATIC_SIGNAL_LIGHT_GREEN = new RegistryObject<>(() -> new StaticSignalLightBlockGreen(BlockBehaviour.Properties.of().strength(1.0f)));
    public static final RegistryObject<Block> STATIC_SIGNAL_LIGHT_BLUE = new RegistryObject<>(() -> new StaticSignalLightBlockBlue(BlockBehaviour.Properties.of().strength(1.0f)));
    public static final RegistryObject<Block> SPOT_LAMP = new RegistryObject<>(() -> new SpotLampBlock(BlockBehaviour.Properties.of().lightLevel(state -> IBlock.getStatePropertySafe(state, LIT) ? 15 : 0).noOcclusion()));
    public static final RegistryObject<Block> STATION_NAME_STANDING = new RegistryObject<>(() -> new StationNameStandingBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> SUBSIDY_MACHINE = new RegistryObject<>(() -> new SubsidyMachineBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<Block> SOUND_LOOPER = new RegistryObject<>(() -> new SoundLooperBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> STATION_CEILING_WRL = new RegistryObject<>(() -> new StationCeilingWRL2Block(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> STATION_CEILING_WRL_SINGLE = new RegistryObject<>(() -> new StationCeilingWRLBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> STATION_CEILING_WRL_STATION_COLOR = new RegistryObject<>(() -> new StationCeilingWRL2Block(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> STATION_CEILING_WRL_SINGLE_STATION_COLOR = new RegistryObject<>(() -> new StationCeilingWRLBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> STATION_CEILING_WRL_POLE = new RegistryObject<>(() -> new StationCeilingWRL2Pole(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> STATION_CEILING_WRL_POLE_SINGLE = new RegistryObject<>(() -> new StationCeilingWRLPole(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> TCL_EMG_STOP_BUTTON = new RegistryObject<>(() -> new TCLEmergencyButtonBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> THALES_TICKET_BARRIER_ENTRANCE = new RegistryObject<>(() -> new ThalesTicketBarrier(true));
    public static final RegistryObject<Block> THALES_TICKET_BARRIER_EXIT = new RegistryObject<>(() -> new ThalesTicketBarrier(false));
    public static final RegistryObject<Block> THALES_TICKET_BARRIER_BARE = new RegistryObject<>(() -> new ThalesTicketBarrierBareBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> TML_EMG_STOP_BUTTON = new RegistryObject<>(() -> new TMLEmergencyButtonBlock(BlockBehaviour.Properties.of().lightLevel(state -> 15).strength(4.0f).noOcclusion()));
    public static final RegistryObject<Block> TRAIN_MODEL_E44 = new RegistryObject<>(() -> new MTRTrainModelBlock(BlockBehaviour.Properties.of().strength(0.5f)));
    public static final RegistryObject<Block> WATER_MACHINE = new RegistryObject<>(() -> new WaterMachineBlock(BlockBehaviour.Properties.of().strength(4.0f).noOcclusion()));

    public static void register() {
        JCMLogger.debug("Registering blocks...");

        JCMRegistry.registerBlock("apg_door_drl", APG_DOOR_DRL);
        JCMRegistry.registerBlock("apg_glass_drl", APG_GLASS_DRL);
        JCMRegistry.registerBlock("apg_glass_end_drl", APG_GLASS_END_DRL);
        JCMRegistry.registerBlockAndItem("auto_iron_door", AUTO_IRON_DOOR, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("butterfly_light", BUTTERFLY_LIGHT, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("buffer_stop", BUFFER_STOP, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("ceiling_slanted", CEILING_SLANTED, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_1", CIRCLE_WALL_1, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_2", CIRCLE_WALL_2, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_3", CIRCLE_WALL_3, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_4", CIRCLE_WALL_4, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_5", CIRCLE_WALL_5, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_6", CIRCLE_WALL_6, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("circle_wall_7", CIRCLE_WALL_7, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("departure_pole", DEPARTURE_POLE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("departure_timer", DEPARTURE_TIMER, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("exit_sign_odd", EXIT_SIGN_ODD, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("exit_sign_even", EXIT_SIGN_EVEN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("fire_alarm", FIRE_ALARM, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("fare_saver", FARE_SAVER, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("helpline_1", HELPLINE_1, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("helpline_2", HELPLINE_2, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("helpline_hkwk", HELPLINE_HKWK, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("helpline_standing", HELPLINE_STANDING, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("helpline_standing_eal", HELPLINE_STANDING_EAL, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("kcr_emg_stop_sign", KCR_EMG_STOP_SIGN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("kcr_enquiry_machine", KCR_ENQUIRY_MACHINE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("kcr_name_sign", KCR_STATION_NAME_SIGN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("kcr_name_sign_station_color", KCR_STATION_NAME_SIGN_STATION_COLOR, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("kcr_trespass_sign", KCR_TRESPASS_SIGN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("lcd_pids", LCD_PIDS, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("light_block", LIGHT_BLOCK, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("light_lantern", LIGHT_LANTERN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("lrt_trespass_sign", LRT_TRESPASS_SIGN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("lrt_inter_car_barrier_left", LRT_INTER_CAR_BARRIER_LEFT, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("lrt_inter_car_barrier_middle", LRT_INTER_CAR_BARRIER_MIDDLE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("lrt_inter_car_barrier_right", LRT_INTER_CAR_BARRIER_RIGHT, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("mtr_enquiry_machine", MTR_ENQUIRY_MACHINE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("mtr_enquiry_machine_wall", MTR_ENQUIRY_MACHINE_WALL, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("mtr_stairs", MTR_STAIRS, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("mtr_trespass_sign", MTR_TRESPASS_SIGN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("operator_button", OPERATOR_BUTTON, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("pids_projector", PIDS_PROJECTOR, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("pids_1a", PIDS_1A, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("rv_pids", RV_PIDS, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("rv_pids_sil_1", RV_PIDS_SIL_1, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("rv_pids_sil_2", RV_PIDS_SIL_2, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("rv_pids_pole", RV_PIDS_POLE, ItemGroups.PIDS);
        JCMRegistry.registerBlockAndItem("rv_enquiry_machine", RV_ENQUIRY_MACHINE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("sil_emg_stop_button", SIL_EMG_STOP_BUTTON, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("signal_light_inverted_1", SIGNAL_LIGHT_INVERTED_RED_ABOVE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("signal_light_inverted_2", SIGNAL_LIGHT_INVERTED_RED_BOTTOM, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("signal_light_red_1", STATIC_SIGNAL_LIGHT_RED_BELOW, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("signal_light_red_2", STATIC_SIGNAL_LIGHT_RED_TOP, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("signal_light_green", STATIC_SIGNAL_LIGHT_GREEN, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("signal_light_blue", STATIC_SIGNAL_LIGHT_BLUE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("spot_lamp", SPOT_LAMP, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_name_standing", STATION_NAME_STANDING, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("subsidy_machine", SUBSIDY_MACHINE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("sound_looper", SOUND_LOOPER, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_ceiling_wrl", STATION_CEILING_WRL, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_ceiling_wrl_single", STATION_CEILING_WRL_SINGLE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_ceiling_wrl_station_color", STATION_CEILING_WRL_STATION_COLOR, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_ceiling_wrl_single_station_color", STATION_CEILING_WRL_SINGLE_STATION_COLOR, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_ceiling_wrl_pole", STATION_CEILING_WRL_POLE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("station_ceiling_wrl_single_pole", STATION_CEILING_WRL_POLE_SINGLE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("tcl_emg_stop_button", TCL_EMG_STOP_BUTTON, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("thales_ticket_barrier_entrance", THALES_TICKET_BARRIER_ENTRANCE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("thales_ticket_barrier_exit", THALES_TICKET_BARRIER_EXIT, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("thales_ticket_barrier_bare", THALES_TICKET_BARRIER_BARE, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("tml_emg_stop_button", TML_EMG_STOP_BUTTON, ItemGroups.MAIN);
        JCMRegistry.registerBlockAndItem("train_model_e44", TRAIN_MODEL_E44, ItemGroups.MAIN);

        JCMRegistry.registerBlockAndItem("water_machine", WATER_MACHINE, ItemGroups.MAIN);
        // Calling this method cause the static class to be loaded, in turn registering the content.
    }

    public static void registerClient() {
        /* Define Render Layer (Texture transparency etc.) */
        JCMRegistryClient.registerBlockRenderType(RenderType.cutout(),
            APG_DOOR_DRL,
            APG_GLASS_DRL,
            APG_GLASS_END_DRL,
            AUTO_IRON_DOOR,
            BUFFER_STOP,
            BUTTERFLY_LIGHT,
            CIRCLE_WALL_1,
            CIRCLE_WALL_2,
            CIRCLE_WALL_3,
            CIRCLE_WALL_4,
            CIRCLE_WALL_5,
            CIRCLE_WALL_6,
            FIRE_ALARM,
            FARE_SAVER,
            KCR_STATION_NAME_SIGN,
            KCR_STATION_NAME_SIGN_STATION_COLOR,
            KCR_EMG_STOP_SIGN,
            MTR_ENQUIRY_MACHINE,
            RV_ENQUIRY_MACHINE,
            RV_PIDS_SIL_1,
            RV_PIDS_SIL_2,
            SUBSIDY_MACHINE,
            SPOT_LAMP,
            HELPLINE_1,
            HELPLINE_2,
            HELPLINE_HKWK,
            HELPLINE_STANDING,
            HELPLINE_STANDING_EAL,
            SIL_EMG_STOP_BUTTON,
            STATION_NAME_STANDING,
            TML_EMG_STOP_BUTTON,
            THALES_TICKET_BARRIER_ENTRANCE,
            THALES_TICKET_BARRIER_EXIT,
            MTR_TRESPASS_SIGN,
            WATER_MACHINE
        );

        JCMRegistryClient.registerBlockRenderType(RenderType.translucent(),
            Blocks.THALES_TICKET_BARRIER_BARE
        );

        /* Station Colored Blocks */
        JCMRegistryClient.registerStationColoredBlock(
            KCR_STATION_NAME_SIGN_STATION_COLOR,
            STATION_NAME_STANDING,
            STATION_CEILING_WRL_STATION_COLOR,
            STATION_CEILING_WRL_SINGLE_STATION_COLOR
        );
    }
}
