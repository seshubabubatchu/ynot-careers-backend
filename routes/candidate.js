const express = require("express");
const { Candidate } = require("../model/candidate");
const mongoose = require("mongoose");
const router = express.Router();

//post a candidate
router.post("/", async (req, res) => {
  try {
    let candidate = new Candidate({
      jobId: req.body.jobId,
      firstName: req.body.candidateDetails.firstName,
      lastName: req.body.candidateDetails.lastName,
      email: req.body.candidateDetails.email,
      gender: req.body.candidateDetails.gender,
      payRate: req.body.candidateDetails.payRate,
      address: {
        city: req.body.candidateDetails.address.city,
        state: req.body.candidateDetails.address.state,
        country: req.body.candidateDetails.address.country,
      },
      skills: req.body.candidateDetails.skills,
    });
    candidate = await candidate.save();
    if (!candidate) return res.status(400).send("Error Creating Candidate");
    return res.status(201).send(candidate);
  } catch (error) {
    console.log(error);
  }
});
router.get("/", async (req, res) => {
  try {
    let allCandidates = await Candidate.find().populate({
      path: "jobId",
      populate: {
        path: "category",
      },
    });
    if (!allCandidates)
      return res.status(400).send("error fetching candidates");
    return res.status(200).send(allCandidates);
  } catch (error) {
    console.log(error);
  }
});
router.get("/:id", async (req, res) => {
  try {
    console.log(req.params.id);
    let candidateDetail = await Candidate.findById(req.params.id).populate({
      path: "jobId",
      populate: {
        path: "category",
      },
    });

    if (!candidateDetail)
      return res.status(400).send("error fetching candidateDetail");
    // console.log(allcandidateDetails);
    return res.status(200).send(candidateDetail);
  } catch (error) {
    console.log(error);
  }
});
router.get("/:id/count", async (req, res) => {
  try {
    console.log(req.params.id);
    let allCandidatesbyJob = await await Candidate.find({
      jobId: req.params.id,
    }).count();

    if (!allCandidatesbyJob)
      return res.status(400).send("error fetching allCandidates byy category");
    // console.log(allCandidates);
    return res.status(200).send({ count: allCandidatesbyJob });
  } catch (error) {
    console.log(error);
  }
});
module.exports = router;
