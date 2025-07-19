import React, { useState } from "react";
import "./App.css";

function App() {
  const [count, setCount] = useState(0);

  const increase = () => {
    if (count < 10) setCount(count + 1);
  };

  const decrease = () => {
    if (count > -10) setCount(count - 1);
  };

  const reset = () => {
    setCount(0);
  };

  return (
    <div className="counter-container">
      <div className="counter-box">
        <h1 className="title">Counter App</h1>
        <div className={`count ${count < 0 ? "negative" : ""}`}>{count}</div>
        <div className="buttons">
          <button className="btn increase" onClick={increase}>Increase</button>
          <button className="btn decrease" onClick={decrease}>Decrease</button>
          <button className="btn reset" onClick={reset}>Reset</button>
        </div>
      </div>
    </div>
  );
}

export default App;
