const express = require("express");
const mongoose = require("mongoose");
const isAuthenticated = require("../helpers/isAuthenticated");
const { Category } = require("../model/category");
const { Job } = require("../model/job");

const router = express.Router();
router.get("/", async (req, res) => {
  try {
    const allJobs = await Job.find().populate("category");
    if (!allJobs) return res.status(500).send("Cannot Fetch Jobs");
    return res.status(200).send(allJobs);
  } catch (error) {
    console.log(error);
  }
});
router.get("/available", async (req, res) => {
  try {
    const allJobs = await Job.find({ display: true }).populate("category");
    if (!allJobs) return res.status(500).send("Cannot Fetch Jobs");
    return res.status(200).send(allJobs);
  } catch (error) {
    console.log(error);
  }
});

router.get("/:id", async (req, res) => {
  try {
    const validJobId = mongoose.Types.ObjectId.isValid(req.params.id);
    if (validJobId) {
      let job = await Job.findById(req.params.id).populate("category");
      if (!job) return res.status(400).send("Cannot fetch job");
      return res.status(200).send(job);
    } else {
      return res.status(400).send("Invalid Job Id");
    }
  } catch (error) {
    console.log(error);
  }
});
router.post("/", isAuthenticated, async (req, res) => {
  try {
    validCategory = Category.findById(req.body.category);
    validIdFormat = mongoose.Types.ObjectId.isValid(req.body.category);
    if (validCategory && validIdFormat) {
      let job = new Job({
        title: req.body.title,
        category: req.body.category,
        location: req.body.location,
        postedDate: req.body.postedDate,
        description: req.body.description,
        descriptionTeaser: req.body.descriptionTeaser,
        hiringType: req.body.hiringType,
        display: req.body.display,
      });
      job = await job.save();
      if (!job) return res.status(500).send("Error Creating job");
      res.status(201).send(job);
    } else {
      return res.status(400).send("Invalid Category");
    }
  } catch (error) {
    console.log(error);
  }
});
router.put("/:id", isAuthenticated, async (req, res) => {
  try {
    validCategory = Category.findById(req.body.category);
    validIdFormat = mongoose.Types.ObjectId.isValid(req.body.category);
    validJob = Job.findById(req.params.id);
    validJobIdFormat = mongoose.Types.ObjectId.isValid(req.params.id);
    if (validJobIdFormat && validJob) {
      if (validCategory && validIdFormat) {
        let job = await Job.findByIdAndUpdate(
          req.params.id,
          {
            title: req.body.title,
            category: req.body.category,
            location: req.body.location,
            postedDate: req.body.postedDate,
            description: req.body.description,
            descriptionTeaser: req.body.descriptionTeaser,

            hiringType: req.body.hiringType,
            display: req.body.display,
          },
          { new: true }
        );
        if (!job) return res.status(500).send("Error Updating job");
        res.status(201).send(job);
      } else {
        return res.status(400).send("Invalid Category Id");
      }
    } else {
      return res.status(400).send("Invalid Job Id");
    }
  } catch (error) {
    console.log(error);
  }
});
router.delete("/:id", isAuthenticated, async (req, res) => {
  try {
    let job = await Job.findByIdAndDelete(req.params.id);
    if (!job) return res.status(500).send("Error Deleting Cateogry");
    res.status(200).send(job);
  } catch (error) {
    console.log(error);
  }
});
router.get("/count/:id", async (req, res) => {
  try {
    let category = await Category.findById(req.params.id);
    if (category) {
      allJobsofCategory = await Job.find({ category: req.params.id }).count();
      return res.send({ count: allJobsofCategory });
    } else {
      return res.send("Invalid Category");
    }
  } catch (error) {
    console.log(error);
  }
});

module.exports = router;
