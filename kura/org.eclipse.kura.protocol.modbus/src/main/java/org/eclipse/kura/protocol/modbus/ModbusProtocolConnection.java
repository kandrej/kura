package org.eclipse.kura.protocol.modbus;

import org.eclipse.kura.KuraConnectionStatus;

public interface ModbusProtocolConnection {

    /**
     * for expedience, can test the status of the connection prior to attempting
     * a command. A connection status of <b>CONNECTED</b> does not assure that a
     * subsequent command will succeed.
     * <p>
     * All protocols must implement this method.
     *
     * @return current connection status as defined in
     *         {@link KuraConnectionStatus KuraConnectionStatus}.
     */
    public int getConnectStatus();
    
    /**
     * attempt to connect to the field device using the provided configuration.
     * Attempts to connect before configuring the connection or any issues with
     * connecting to the field device will result in an exception being thrown.
     * This includes things like a networking failure in the case of a protocol
     * configured to access the field device of a network.
     * <p>
     * Refer to {@link #getConnectStatus() getConnectStatus} to determine if the
     * connection is completed.
     * <p>
     * All protocols must implement this method.
     *
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#INVALID_CONFIGURATION}
     *             this operates on the basic assumption that access to a device
     *             should exist, if the device is unreachable, it is interpreted
     *             as a failure of the configuration.
     */
    public void connect() throws ModbusProtocolException;

    /**
     * attempt to disconnect from the field device. This should close any port
     * used exclusively for this protocol to talk with its attached device.
     * Attempting to close an already closed connection is not invalid.
     * <p>
     * All protocols must implement this method.
     *
     * @throws ModbusProtocolException
     *
     * @see #getConnectStatus()
     */
    public void disconnect() throws ModbusProtocolException;
    
    /**
     * <b>Modbus function 01</b><br>
     * Read 1 to 2000 contiguous status of coils from the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            starting address
     * @param count
     *            quantity of coils
     * @return an array of booleans representing the requested data points.
     *         <b>true</b> for a given point if the point is set, <b>false</b>
     *         otherwise.
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public boolean[] readCoils(int unitAddr, int dataAddress, int count) throws ModbusProtocolException;

    /**
     * <b>Modbus function 02</b><br>
     * Read 1 to 2000 contiguous status of discrete inputs from the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            starting address
     * @param count
     *            quantity of inputs
     * @return an array of booleans representing the requested data points.
     *         <b>true</b> for a given point if the point is set, <b>false</b>
     *         otherwise.
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public boolean[] readDiscreteInputs(int unitAddr, int dataAddress, int count) throws ModbusProtocolException;

    /**
     * <b>Modbus function 05</b><br>
     * write a single output to either ON or OFF in the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            Output address.
     * @param data
     *            Output value (boolean) to write.
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public void writeSingleCoil(int unitAddr, int dataAddress, boolean data) throws ModbusProtocolException;

    /**
     * <b>Modbus function 15 (0x0F)</b><br>
     * write multiple coils in a sequence of coils to either ON or OFF in the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            Starting Output address.
     * @param data
     *            Outputs value (array of boolean) to write.
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public void writeMultipleCoils(int unitAddr, int dataAddress, boolean[] data) throws ModbusProtocolException;

    /**
     * <b>Modbus function 03</b><br>
     * Read contents of 1 to 125 contiguous block of holding registers from the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            starting address
     * @param count
     *            quantity of registers (maximum 0x7D)
     * @return an array of int representing the requested data points (data registers on 2 bytes).
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public int[] readHoldingRegisters(int unitAddr, int dataAddress, int count) throws ModbusProtocolException;

    /**
     * <b>Modbus function 04</b><br>
     * Read contents of 1 to 125 contiguous block of input registers from the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            starting address
     * @param count
     *            quantity of registers (maximum 0x7D)
     * @return an array of int representing the requested data points (data registers on 2 bytes).
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public int[] readInputRegisters(int unitAddr, int dataAddress, int count) throws ModbusProtocolException;

    /**
     * <b>Modbus function 06</b><br>
     * write a single holding register in the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            Output address.
     * @param data
     *            Output value (2 bytes) to write.
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public void writeSingleRegister(int unitAddr, int dataAddress, int data) throws ModbusProtocolException;

    /**
     * <b>Modbus function 07</b><br>
     * read the content of 8 Exception Status outputs in the field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public boolean[] readExceptionStatus(int unitAddr) throws ModbusProtocolException;

    /**
     * <b>Modbus function 11 (0x0B)</b><br>
     * Get a status word and an event count from the device.<br>
     * Return values in a ModbusCommEvent.
     * <p>
     * 
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     * @see ModbusCommEvent
     */
    public ModbusCommEvent getCommEventCounter(int unitAddr) throws ModbusProtocolException;

    /**
     * <b>Modbus function 12 (0x0C)</b><br>
     * Get a status word, an event count, a message count and a list of event bytes
     * from the device.<br>
     * Return values in a ModbusCommEvent.
     * <p>
     * 
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     * @see ModbusCommEvent
     */
    public ModbusCommEvent getCommEventLog(int unitAddr) throws ModbusProtocolException;

    /**
     * <b>Modbus function 16 (0x10)</b><br>
     * write a block of contiguous registers (1 to 123) in the attached field device.
     * <p>
     *
     * @param unitAddr
     *            modbus slave address (must be unique in the range 1 - 247)
     * @param dataAddress
     *            Output address.
     * @param data
     *            Registers value (array of int converted in 2 bytes values) to write.
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#NOT_CONNECTED}
     *             current connection is in a status other than <b>CONNECTED</b>
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#TRANSACTION_FAILURE}
     *             should include a protocol specific message to help clarify
     *             the cause of the exception
     */
    public void writeMultipleRegister(int unitAddr, int dataAddress, int[] data) throws ModbusProtocolException;
}
