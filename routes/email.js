const dotenv = require("dotenv").config();
const express = require("express");
const nodemailer = require("nodemailer");
const router = express.Router();

router.post("/email", (req, res) => {
  const transport = nodemailer.createTransport({
    host: "smtp.zoho.in",
    secure: true,
    port: 465,
    auth: {
      user: process.env.zohomail,
      pass: process.env.zohopassword,
    },
  });
  const mailoptions = {
    from: `'YNOT Careers'<${process.env.zohomail}>`,
    to: req.body.email,
    subject: "Test Subject",
    html: `
        <p>
          Thank You for Subscribing to YNOT Careers
      </p>
      <p>
          All the Best for you Future
      </p>
        `,
  };
  transport.sendMail(mailoptions, (error, info) => {
    if (error) {
      console.log(error);
    } else {
      console.log("sent" + info.response);
      return res.send(info.response);
    }
  });
});
router.post("/testing", (req, res) => {
  //   console.log("logging", req.body);
  //   res.send(req.body);
  const transport = nodemailer.createTransport({
    host: "smtp.zoho.in",
    secure: true,
    port: 465,
    auth: {
      user: process.env.zohomail,
      pass: process.env.zohopassword,
    },
  });
  const mailoptions = {
    from: `'YNOT Careers'<${process.env.zohomail}>`,
    to: req.body.userDetails.email,
    subject: "Check This Job at YNOT",
    html: req.body.emailbody,
  };
  transport.sendMail(mailoptions, (error, info) => {
    if (error) {
      console.log(error);
    } else {
      console.log("sent" + info.response);
      return res.send(info.response);
    }
  });
});
router.post("/scheduleInterview", (req, res) => {
  //   console.log("logging", req.body);
  //   res.send(req.body);
  const transport = nodemailer.createTransport({
    host: "smtp.zoho.in",
    secure: true,
    port: 465,
    auth: {
      user: process.env.zohomail,
      pass: process.env.zohopassword,
    },
  });
  const mailoptions = {
    from: `'YNOT Careers'<${process.env.zohomail}>`,
    to: req.body.userDetails.candidate.email,
    subject: `Interview Scheduled `,
    html: `<p>Greetings from YNOT your Interview has been scheduled</p>
    <p>Hai ${req.body.userDetails.candidate.firstName}</p>
    <p>Our HR Team Would Shortly Contact you for confirmation of Data and Time</p>
    Your Interview Link Would be : <a href='${req.body.interviewLink}'>Interview Link</a>
    <p>Thanks and Regards</p>
    <small>YNOT</small>
    `,
  };
  transport.sendMail(mailoptions, (error, info) => {
    if (error) {
      console.log(error);
    } else {
      console.log("sent" + info.response);
      return res.send(info.response);
    }
  });
});
router.post("/jtc", (req, res) => {
  //   console.log("logging", req.body);
  //   res.send(req.body);
  const transport = nodemailer.createTransport({
    host: "smtp.zoho.in",
    secure: true,
    port: 465,
    auth: {
      user: process.env.zohomail,
      pass: process.env.zohopassword,
    },
  });
  const mailoptions = {
    from: `'YNOT Careers'<${process.env.zohomail}>`,
    to: req.body.userDetails.email,
    subject: `JTC Subscribed `,
    html: `<p>Hai ${req.body.userDetails.firstName} Thank You for Subscribing to YNOT Careers JTC</p>
   
    <p>You will receive our latest notifications to your email</p>
    <p>Thank You</p>
    <p>YNOT</p>
    `,
  };
  transport.sendMail(mailoptions, (error, info) => {
    if (error) {
      console.log(error);
    } else {
      console.log("sent" + info.response);
      return res.send(info.response);
    }
  });
});
module.exports = router;
