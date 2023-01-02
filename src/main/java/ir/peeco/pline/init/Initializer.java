package ir.peeco.pline.init;

import ir.peeco.pline.models.TblSipProfile;
import ir.peeco.pline.models.TblSipProfileDetails;
import ir.peeco.pline.models.TblSipUser;
import ir.peeco.pline.models.PlineUser;
import ir.peeco.pline.models.TblSipParameter;
import ir.peeco.pline.pline.PlineTools;
import ir.peeco.pline.pline.fileGenratorTools.FileGenrator;
import ir.peeco.pline.repositories.PlineUsersRepository;
import ir.peeco.pline.repositories.SipParameterRepository;
import ir.peeco.pline.repositories.SipProfileDetailsRepository;
import ir.peeco.pline.repositories.SipProfilesRepository;
import ir.peeco.pline.repositories.SipUsersRepository;
import ir.peeco.pline.tools.GlobalsTools;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer {
    @Value("${spring.banner.location}")
    private String banner;

    private PlineUsersRepository plineUsersRepository;
    private SipProfilesRepository sipProfilesRepository;
    private SipProfileDetailsRepository profileDetailsRepository;
    private SipParameterRepository parameterRepository;
    private FileGenrator fileGenrator;
    private SipUsersRepository sipUsersRepository;

    @Autowired
    public void setSipUsersRepository(SipUsersRepository sipUsersRepository) {
        this.sipUsersRepository = sipUsersRepository;
    }

    @Autowired
    public void setFileGenrator(FileGenrator fileGenrator) {
        this.fileGenrator = fileGenrator;
    }

    @Autowired
    public void setParameterRepository(SipParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @Autowired
    public void setUsersRepository(PlineUsersRepository usersRepository) {
        this.plineUsersRepository = usersRepository;
    }

    @Autowired
    public void setSipProfilesRepository(SipProfilesRepository sipProfilesRepository) {
        this.sipProfilesRepository = sipProfilesRepository;
    }

    @Autowired
    public void setProfileDetailsRepository(SipProfileDetailsRepository profileDetailsRepository) {
        this.profileDetailsRepository = profileDetailsRepository;
    }

    private PlineTools plineTools;

    @Autowired
    public void setPlineTools(PlineTools plineTools) {
        this.plineTools = plineTools;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {

            // ArrayList<TblSipParameter> list = new ArrayList<>();
            // var data = (HashMap<String, Object>)
            // GlobalsTools.getSipParameters().get("sip_profiles");
            // data.forEach((k, v) -> {
            // for (var string : ((ArrayList) v)) {
            // TblSipParameter p = new TblSipParameter();
            // p.setGroup(k);
            // var f = ((ArrayList) string);
            // p.setId(null);
            // p.setName(f.get(0).toString());
            // p.setType(f.get(1).toString());
            // p.setOptions(f.get(2) == null ? "" : f.get(2).toString());
            // p.setDefaultValue(f.get(3).toString());
            // p.setDescription(f.get(4).toString());
            // list.add(p);
            // }
            // });
            // ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            // String json = ow.writeValueAsString(list);
            // PrintWriter p = new PrintWriter("./pjsip_parameters.json");
            // p.println(json);
            // p.close();

            if (parameterRepository.count() == 0) {
                Iterable<TblSipParameter> iterator = Arrays.asList(GlobalsTools.getpjSipParameters());
                parameterRepository.saveAll(iterator);
            }

            plineTools.getRandomString(10);

            if (plineUsersRepository.findByUsername("admin") == null) {
                PlineUser plineUser = new PlineUser();
                plineUser.setFullname("Administrator");
                plineUser.setUsername("admin");
                plineUser.setPassword(GlobalsTools.MD5("admin"));
                plineUser.setEnable(true);
                plineUser.setRoles(new String[] { "ADMIN", "CLIENT" });
                plineUsersRepository.save(plineUser);
            }

            if (sipProfilesRepository.count() == 0) {
                TblSipProfile profileU = new TblSipProfile();
                profileU.setName("Sip User Profile");
                profileU.setDescription(".:: Default Sip User Profile ::.");
                profileU.setEnable(true);
                sipProfilesRepository.save(profileU);

                TblSipProfile profileT = new TblSipProfile();
                profileT.setName("Sip Trunks Profile");
                profileT.setDescription(".:: Default Sip Trunk Profile ::.");
                profileT.setEnable(true);
                sipProfilesRepository.save(profileT);

                Object[][] def = new Object[][] {
                        { profileU, "endpoint", "context", "default" },
                        { profileU, "endpoint", "allow", "[\"ulaw\",\"alaw\",\"gsm\",\"h264\",\"h263\"]" },
                        { profileU, "endpoint", "device_state_busy_at", "2" },
                        { profileU, "auth", "auth_type", "userpass" },
                        { profileU, "aor", "max_contacts", "1" },
                        { profileU, "transport", "protocol", "udp" },
                        { profileU, "transport", "bind", "0.0.0.0:5060" },
                        { profileU, "endpoint", "dtmf_mode", "auto" },

                        { profileT, "endpoint", "context", "public" },
                        { profileT, "endpoint", "disallow", "all" },
                        { profileT, "endpoint", "allow", "ulaw,alaw" },
                        { profileT, "endpoint", "device_state_busy_at", "30" },
                        { profileT, "auth", "auth_type", "userpass" },
                        { profileT, "aor", "max_contacts", "1" },
                        { profileT, "transport", "protocol", "udp" },
                        { profileT, "transport", "bind", "0.0.0.0:5080" },
                        { profileT, "endpoint", "dtmf_mode", "auto" },
                };

                for (Object[] objects : def) {
                    TblSipProfileDetails d = new TblSipProfileDetails();
                    d.sipProfile = (TblSipProfile) objects[0];
                    d.type = objects[1].toString();
                    d.key = objects[2].toString();
                    d.value = objects[3].toString();
                    profileDetailsRepository.save(d);
                }

                for (int i = 1001; i < 1005; i++) {
                    var user = new TblSipUser();
                    user.setAcl("192.168.0.0/24,172.16.0.0/22");
                    user.setUid(String.valueOf(i));
                    user.setPassword(String.valueOf(i));
                    user.setSipProfile(profileU);
                    user.setEffectiveCallerIdName("user " + i);
                    user.setEffectiveCallerIdNumber(String.valueOf(i));
                    user.setOutboundCallerIdName("pline " + i);
                    user.setOutboundCallerIdNumber(String.valueOf(i));
                    sipUsersRepository.save(user);
                }
            }
            if (banner == null || banner.trim().isEmpty())
                System.out.println("\n" +
                        "\t\t\t***********************************************\n" +
                        "\t\t\t# ██████╗       ██╗     ██╗███╗   ██╗███████╗ #\n" +
                        "\t\t\t# ██╔══██╗      ██║     ██║████╗  ██║██╔════╝ #\n" +
                        "\t\t\t# ██████╔╝█████╗██║     ██║██╔██╗ ██║█████╗   #\n" +
                        "\t\t\t# ██╔═══╝ ╚════╝██║     ██║██║╚██╗██║██╔══╝   #\n" +
                        "\t\t\t# ██║           ███████╗██║██║ ╚████║███████╗ #\n" +
                        "\t\t\t# ╚═╝           ╚══════╝╚═╝╚═╝  ╚═══╝╚══════╝ #\n" +
                        "\t\t\t***********************************************\n");
            fileGenrator.GratePjsip();
        };
    };

}
