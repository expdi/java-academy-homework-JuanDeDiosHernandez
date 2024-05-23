package expeditors.backend.adoption.dao;

import expeditors.backend.adoption.service.AdopterService;
import expeditors.backend.adoption.dao.inmemory.InMemoryAdopterDAO;
import expeditors.backend.adoption.dao.jpa.JPAAdopterDAO;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class DAOFactory {

   private static Map<String, Object> objects = new ConcurrentHashMap<>();
   private static String profile;

   static {
      ResourceBundle bundle = ResourceBundle.getBundle("backend");
      profile = bundle.getString("expeditors.profile");
   }

   public static AdopterDAO adopterDAO() {
      var result = switch(profile) {
         case "dev" -> (AdopterDAO)objects.computeIfAbsent("adopterDAO", (key) -> { return new InMemoryAdopterDAO(); });
         case "prod" -> (AdopterDAO)objects.computeIfAbsent("adopterDAO", (key) -> { return new JPAAdopterDAO(); });
         default -> throw new RuntimeException("Unknown profile: " + profile);
      };

      return result;
   }

   public static AdopterService adopterService() {
//      AdopterService ss = (AdopterService)objects.computeIfAbsent("adopterService", key -> {
//         AdopterService newService = new AdopterService();
//            AdopterDAO dao = adopterDAO();
//            newService.setAdopterDAO(dao);
//            return newService;
//      });
//
//      return ss;
      return null;
   }
}
