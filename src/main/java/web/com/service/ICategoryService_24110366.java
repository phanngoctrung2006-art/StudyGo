package web.com.service;

import web.com.model.Category_24110366;
import java.util.List;

public interface ICategoryService_24110366 {
    List<Category_24110366> findAll();
    List<Category_24110366> findAllWithVideoCount();
    Category_24110366 findById(int id);
    int countVideosByCategory(int categoryId);
}
