<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Company - Job Application Service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>

        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
    
        body {
            background-image: url('https://i.imgur.com/as1eDDA.png');
        background-size: cover;
        font-family:'Poppins', 'sans-serif';
        font-optical-sizing: auto;
        font-weight: auto;
        font-style: normal;
        }
        .profile-header {
            background-color: white;
            padding: 20px;
            text-align: center;
            border-radius: 10px;
            margin-bottom: 20px;
            margin-top: 125px;
        }
        .profile-header img {
            width: 300px;
            border-radius: 50%;
        }
        .post, .description, .employment-history {
            border: 1px solid #e3e3e3;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 15px;
            background-color: #fff;
        }
    
        .btn {
        transition: 0.5s;
        background-size: 200% auto;
        transition: 0.5s;
    }
    
    .btn:hover {
        background-position: right center;
    }
    
    .btn-1 {
        background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
        font-style: bold;
    }
    
    .navi {
        margin-top: 23px;
        transition: 0.5s;
        background-size: 200% auto;
        transition: 0.5s;
        background-image: linear-gradient(to right, #91CB9C 0%, #FFCECE 51%, #91CB9C 100%);
        font-style: bold;
        border-radius: 7px;
        width: 110px;
        height: auto;
        text-align: center;
        display: inline-block;
        padding: 0.15rem .5rem; 
    }
    
    .navi:hover {
        background-position: right center;
    }
    
    .nav-link {
        margin-top: 23px;
        height: 100%; 
        padding: 0.15rem .5rem;
    }
    .nav-link img {
        border: 2px solid #80e7b1;
        border-radius: 50%;
    }
    
    .nav-brand {
        height: 100%; 
        padding: 0.15rem .5rem;
    }
    
    .navbar {
    border-bottom: 2px solid #ccc; 
    padding-bottom: 10px; 
    background-image: url('images/background.png');
    background-size: 97%;
    }
    
    .anyButton:hover {
        background-position: right center;
    }
    </style>
    </head>
    <body>
    <header>
        
    </header>

    <div class="container mt-5">
        <!-- Navigation Bar -->
        <div class="profile-header text-center rounded-5 shadow-sm">
            <img src="https://i.imgur.com/uQNIcJO.png" alt="Logo" width="100">
            <h1 class="fw-bold text-black">Create a Job Post</h1>
        </div>
    
    <div class="bg-white p-4 rounded-5 shadow-sm row mt-4">
        <div class="col-md-8 offset-md-2">
            <form action="makePost" method="post">
                <div class="mb-3">
                    <label for="companyTitle" class="form-label">Title of Post</label>
                    <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill" name="title" required>
                </div>
                  <div class="mb-3">
                    <label for="companyAddress" class="form-label">Select Category</label>
                   <select class="form-select form-select-lg bg-light fs-6 rounded-pill" name="category" aria-label="Default select example">
                        <option selected hidden disabled></option>
                        <option value="Arts">Arts</option>
                        <option value="Business">Business</option>
                        <option value="Communication">Communication</option>
                        <option value="Education">Education</option>
                        <option value="Hopitality">Hospitality</option>
                        <option value="Information management">Information technology</option>
                        <option value="Law enforcement">Law enforcement</option>
                        <option value="Sales and marketing">Sales and marketing</option>
                        <option value="Science">Science</option>
                        <option value="Transportation">Transportation</option>
                      </select>
                    </div>
                    <div class="mb-3">
                    <label for="companyAddress" class="form-label">Enter address/location</label>
                    <input type="text" class="form-control form-control-lg bg-light fs-6 rounded-pill"
                              name="address">
                  </div>
                    <div class="input-group mb-3 form-group">
               <textarea class="form-control mt-3" name="desc" rows="4" cols="50" placeholder="Description of work" required></textarea>
              </div>
              <div class="justify-content-center mx-auto text-center mb-3">
            <% if (request.getAttribute("errorMessage4") != null) { %>
        	<small class="text-danger fw-bold"><%= request.getAttribute("errorMessage4") %></small>
    		<% } else if (request.getAttribute("successMessage3") != null) { %>
    		<small class="text-success fw-bold"><%= request.getAttribute("successMessage3") %></small>
    		<% } %>
    		</div>
                
                <button type="submit" class="btn btn-1 fw-bold">Submit</button>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
