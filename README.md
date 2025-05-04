# MaxChain

**MaxChain** is a simple Java-based blockchain implementation. It’s built for educational purposes and covers core blockchain mechanics: blocks, transactions, Merkle trees, digital signatures, and proof-of-work.

## Features

- Block creation and mining
- SHA-256 hashing
- Transaction system with digital signatures
- Public/private key wallet implementation
- Merkle root calculation for block integrity
- UTXO-based model
- Proof-of-Work with adjustable difficulty

## Project Structure

- `Block.java` – Defines the structure and mining of a block  
- `Transaction.java` – Handles transaction creation, signing, and verification  
- `Wallet.java` – Manages key generation and sending funds  
- `TransactionInput.java` / `TransactionOutput.java` – Handle inputs and outputs for the UTXO model  
- `StringUtil.java` – Utility functions for hashing, key conversion, and signatures  
- `MaxChain.java` – Main runner for initializing and demonstrating the blockchain

## Requirements

- Java 8 or higher  
- [Gson](https://github.com/google/gson) (for JSON serialization)  
- [Bouncy Castle](https://www.bouncycastle.org/java.html) (for cryptography support)

## License

MIT License. Do whatever you want, just don’t blame me if it breaks.

## Getting Started

Clone the repo:

```bash
git clone https://github.com/maxeagan22/maxchain-java.git
cd maxchain-java
