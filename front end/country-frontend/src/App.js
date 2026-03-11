import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css'; 

function App() {
  const [countries, setCountries] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCountry, setSelectedCountry] = useState(null); 

  useEffect(() => {
    fetchCountries();
  }, [searchTerm]); 

  const fetchCountries = async () => {
    try {
  
      const response = await axios.get(`http://localhost:8080/api/countries?search=${searchTerm}`);
      setCountries(response.data);
    } catch (error) {
      console.error("Error fetching data: ", error);
    }
  };

  return (
    <div className="App" style={{ padding: '20px' }}>
      <h1>Country Explorer</h1>

      {/* 2. Search Input */}
      <input
        type="text"
        placeholder="Search by country name..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{ padding: '10px', width: '300px', marginBottom: '20px' }}
      />

      {/* 1. Countries Table */}
      <table border="1" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th>Flag</th>
            <th>Name</th>
            <th>Capital</th>
            <th>Region</th>
            <th>Population</th>
          </tr>
        </thead>
        <tbody>
          {countries.map((country, index) => (
            <tr key={index} onClick={() => setSelectedCountry(country)} style={{ cursor: 'pointer' }}>
              <td><img src={country.flag} alt="flag" width="50" /></td>
              <td>{country.name}</td>
              <td>{country.capital}</td>
              <td>{country.region}</td>
              <td>{country.population.toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* 3. Country Details Modal */}
      {selectedCountry && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>{selectedCountry.name} Details</h2>
            <img src={selectedCountry.flag} alt="flag" width="150" />
            <p><strong>Capital:</strong> {selectedCountry.capital}</p>
            <p><strong>Region:</strong> {selectedCountry.region}</p>
            <p><strong>Population:</strong> {selectedCountry.population.toLocaleString()}</p>
            <button onClick={() => setSelectedCountry(null)}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;