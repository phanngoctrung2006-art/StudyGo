package web.com.service;

import web.com.dao.CategoryDaoImpl_24110366;
import web.com.dao.ICategoryDao_24110366;
import web.com.model.Category_24110366;

import java.util.List;

public class CategoryServiceImpl_24110366 implements ICategoryService_24110366 {

    private final ICategoryDao_24110366 categoryDao;

    public CategoryServiceImpl_24110366() {
        this.categoryDao = new CategoryDaoImpl_24110366();
    }

    public CategoryServiceImpl_24110366(ICategoryDao_24110366 categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category_24110366> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24110366> findAllWithVideoCount() {
        return categoryDao.findAllWithVideoCount();
    }

    @Override
    public Category_24110366 findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public int countVideosByCategory(int categoryId) {
        return categoryDao.countVideosByCategory(categoryId);
    }
}
