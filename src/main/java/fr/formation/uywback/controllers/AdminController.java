package fr.formation.uywback.controllers;

import fr.formation.uywback.controllers.form.MyUploadForm;
import fr.formation.uywback.models.game.Ressource;
import fr.formation.uywback.repository.RessourceRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RessourceRepository ressourceDao;

    @GetMapping("/ressources/list")
    public String findAll(Model model) {
        List<Ressource> ressources = this.ressourceDao.findAll();
        model.addAttribute("ressources", ressources);

        return "ressources/list";
    }

    // GET: Show upload form page.
    @RequestMapping(value = "/ressources/add", method = RequestMethod.GET)
    public String uploadOneFileHandler(Model model) {

        MyUploadForm myUploadForm = new MyUploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "ressources/add";
    }

    // POST: Do Upload
    @RequestMapping(value = "/ressources/add", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
        int n = (int)(Math.random() * 100000);
        String uploadRootPath = "src/main/resources/static/images/";
        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = myUploadForm.getFileDatas();

        for (MultipartFile fileData : fileDatas) {

            // Client File Name
            String name = FilenameUtils.getBaseName(fileData.getOriginalFilename());
            String extension = FilenameUtils.getExtension(fileData.getOriginalFilename());
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                try {
                    // Create the file at server
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + n + "-" + name + "." + extension);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                } catch (Exception e) {
                }
            }
        }

        Ressource newRessource = new Ressource();

        String nameFile = FilenameUtils.getBaseName(fileDatas[0].getOriginalFilename());
        String extensionFile = FilenameUtils.getExtension(fileDatas[0].getOriginalFilename());

        newRessource.setName(myUploadForm.getName());
        newRessource.setType(myUploadForm.getType());
        newRessource.setPath(n + "-" + nameFile + "." + extensionFile);
        ressourceDao.save(newRessource);

        return "redirect:/admin/ressources/list";
    }


    @GetMapping("/ressources/delete/{id}")
    public String deleteById(@PathVariable long id) {
        this.ressourceDao.deleteById(id);

        return "redirect:/admin/ressources/list";
    }
}
