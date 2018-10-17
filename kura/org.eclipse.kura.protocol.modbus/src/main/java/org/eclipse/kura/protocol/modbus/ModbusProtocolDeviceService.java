/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *******************************************************************************/
package org.eclipse.kura.protocol.modbus;

import java.util.Properties;

/**
 * OSGI service providing a connection to a device via Serial link (RS232/RS485) or Ethernet using Modbus protocol.
 * This service implements a subset of Modbus Application Protocol as defined by Modbus Organization :
 * http://www.modbus.org/specs.php.<br>
 * For the moment in Ethernet mode, only RTU over TCP/IP is supported
 * <p>
 * Function codes implemented are :
 * <ul>
 * <li>01 (0x01) readCoils(int dataAddress, int count) : Read 1 to 2000 max contiguous status of coils from the attached
 * field device.
 * It returns an array of booleans representing the requested data points.
 * <li>02 (0x02) readDiscreteInputs(int dataAddress, int count) : Read 1 to 2000 max contiguous status of discrete
 * inputs
 * from the attached field device. It returns an array of booleans representing the requested data points.
 * <li>03 (0x03) readHoldingRegisters(int dataAddress, int count) : Read contents of 1 to 125 max contiguous block of
 * holding
 * registers from the attached field device. It returns an array of int representing the requested data points
 * (data registers on 2 bytes).
 * <li>04 (0x04) readInputRegisters(int dataAddress, int count) : Read contents of 1 to 125 max contiguous block of
 * input registers
 * from the attached field device. It returns an array of int representing the requested data points (data registers on
 * 2 bytes).
 * <li>05 (0x05) writeSingleCoil(int dataAddress, boolean data) : Write a single output to either ON or OFF in the
 * attached field
 * device.
 * <li>06 (0x06) writeSingleRegister(int dataAddress, int data) : write a single holding register in the attached field
 * device.
 * <li>07 (0x07) readExceptionStatus() : read the content of 8 Exception Status outputs in the field
 * device.
 * <li>11 (0x0B) getCommEventCounter() : Get a status word and an event count from the field
 * device.
 * <li>12 (0x0C) getCommEventLog() : Get a status word, an event count, a message count and a list of event bytes from
 * the field
 * device.
 * <li>15 (0x0F) writeMultipleCoils(int dataAddress, boolean[] data) : Write multiple coils in a sequence of coils to
 * either
 * ON or OFF in the attached field device.
 * <li>16 (0x10) writeMultipleRegister(int dataAddress, int[] data) : write a block of contiguous registers (1 to 123)
 * in the attached
 * field device.
 * </ul>
 */

public interface ModbusProtocolDeviceService {

    /**
     * name of this service
     */
    public static final String SERVICE_NAME = ModbusProtocolDeviceService.class.getName();

    /**
     * returns the unit name given in the configureProtocol call. Prior to
     * configuration, this method will return the built-in name of the protocol,
     * the same as returned by getProtocolName.
     *
     * @return assigned unit name
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString();

    /**
     * returns the protocol name for the specific protocol implemented. This
     * name should follow Java member naming conventions, so the first (or only)
     * part of the name should be all lower case, all subsequent parts to the
     * name should begin with an upper case and continue with lower case.
     *
     * @return name following the above rules
     */
    public String getProtocolName();

    /**
     * Configure access to the physical device.
     *
     * @param connectionConfig
     *            (key/value pairing directly from configuration file)
     *            <ul>
     *            <li>connectionType : serial = "RS232" or Ethernet = "TCP-RTU" = RTU over TCP/IP or 
     *            "TCP/IP" = real MODBUS-TCP/IP
     *            </ul>
     *            <br>for SERIAL mode :
     *            <ul>
     *            <li>port : Name of the port ("/dev/ttyUSB0")
     *            <li>baudRate : baudrate
     *            <li>stopBits : number of stopbits
     *            <li>parity : parity mode (0=none, 1=odd, 2=even)
     *            <li>bitsPerWord : number of bits per word
     *            </ul>
     *            <br>for ETHERNET mode :
     *            <ul>
     *            <li>port : TCP port to be used
     *            <li>ipAddress : the 4 bytes IP address of the field device (xxx.xxx.xxx.xxx)
     *            </ul>
     *            <br>Modbus properties :
     *            <ul>
     *            <li>transmissionMode : modbus transmission mode, can be RTU or ASCII, in Ethernet mode only RTU is
     *            supported
     *            <li>respTimeout : Timeout in milliseconds on a question/response request.
     *            </ul>
     *            
     * @throws ModbusProtocolException with a {@link ModbusProtocolErrorCode#INVALID_CONFIGURATION}
     *             unspecified problem with the configuration
     */
    public ModbusProtocolConnection configureConnection(Properties connectionConfig) throws ModbusProtocolException;

}
