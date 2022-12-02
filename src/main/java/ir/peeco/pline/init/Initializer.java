package ir.peeco.pline.init;

import ir.peeco.pline.models.TblSipProfile;
import ir.peeco.pline.models.TblSipProfileDetails;
import ir.peeco.pline.models.PlineUser;
import ir.peeco.pline.pline.PlineTools;
import ir.peeco.pline.repositories.PlineUsersRepository;
import ir.peeco.pline.repositories.SipProfileDetailsRepository;
import ir.peeco.pline.repositories.SipProfilesRepository;
import ir.peeco.pline.tools.GlobalsTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer {

    private PlineUsersRepository plineUsersRepository;
    private SipProfilesRepository sipProfilesRepository;
    private SipProfileDetailsRepository profileDetailsRepository;

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

            plineTools.getRandomString(10);

            if (plineUsersRepository.findByUsername("admin") == null) {
                PlineUser plineUser = new PlineUser();
                plineUser.setFullname("Administrator");
                plineUser.setUsername( "admin");
                plineUser.setPassword(GlobalsTools.MD5("admin"));
                plineUser.setEnable(true);
                plineUser.setRoles(new String[] { "ADMIN", "CLIENT" });
                plineUsersRepository.save(plineUser);
            }

            if (sipProfilesRepository.count() == 0) {
                TblSipProfile profileU = new TblSipProfile();
                profileU.name = "Sip User Profile";
                profileU.description = ".:: Default Sip User Profile ::.";
                sipProfilesRepository.save(profileU);

                TblSipProfile profileT = new TblSipProfile();
                profileT.name = "Sip Trunks Profile";
                profileT.description = ".:: Default Sip Trunk Profile ::.";
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
            }

            System.out.println("\n" +
                    "\t\t\t***********************************************\n" +
                    "\t\t\t# ██████╗       ██╗     ██╗███╗   ██╗███████╗ #\n" +
                    "\t\t\t# ██╔══██╗      ██║     ██║████╗  ██║██╔════╝ #\n" +
                    "\t\t\t# ██████╔╝█████╗██║     ██║██╔██╗ ██║█████╗   #\n" +
                    "\t\t\t# ██╔═══╝ ╚════╝██║     ██║██║╚██╗██║██╔══╝   #\n" +
                    "\t\t\t# ██║           ███████╗██║██║ ╚████║███████╗ #\n" +
                    "\t\t\t# ╚═╝           ╚══════╝╚═╝╚═╝  ╚═══╝╚══════╝ #\n" +
                    "\t\t\t***********************************************\n");
        };
    };

}
