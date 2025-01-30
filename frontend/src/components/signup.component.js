import React, { useState } from 'react';

export default function SignUp() {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [birthdate, setBirthdate] = useState('');
    const [address, setAddress] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
  
    const validateForm = () => {
        if (!firstName || !lastName || !birthdate || !address || !email || !password) {
          setError('All fields are required');
          return false;
        }
        setError('');
        return true;
      };
    
      const handleSubmit = async (event) => {
        event.preventDefault();
        if (!validateForm()) return;
    
        const formDetails = {
          'firstname': firstName,
          'lastname': lastName,
          'birthdate': birthdate,
          'address': address,
          'email': email,
          'password': password};
    
        try {
          const response = await fetch('http://localhost:8080/api/v1/users', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDetails),
          });
    
          if (!response.ok) {
            const errorData = await response.json();
            setError(errorData.detail || 'SignUp failed!');
          }
        } catch (error) {
          setError('An error occurred. Please try again later.');
        }
      };

    return (
        <form onSubmit={handleSubmit}>
        <h3>Sign Up</h3>
        <div className="mb-3">
          <label>First name</label>
          <input
            type="text"
            className="form-control"
            placeholder="First name"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label>Last name</label>
          <input 
            type="text" 
            className="form-control" 
            placeholder="Last name"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label>Birthdate 2000-03-16T08:30:00.000Z</label>
          <input
            type="text"
            className="form-control"
            placeholder="BirthDate"
            value={birthdate}
            onChange={(e) => setBirthdate(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label>Address</label>
          <input
            type="text"
            className="form-control"
            placeholder="Address"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label>Email address</label>
          <input
            type="email"
            className="form-control"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
            className="form-control"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className="d-grid">
          <button type="submit" className="btn btn-primary">
            Sign Up
          </button>
        </div>
        <p className="forgot-password text-right">
          Already registered <a href="/sign-in">sign in?</a>
        </p>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
    );
}