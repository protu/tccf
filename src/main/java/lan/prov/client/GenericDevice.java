package lan.prov.client;

public class GenericDevice {

	/**
	 * Device ID
	 **/

	private String OUI = "02DA41";
	private String Manufacturer = "Fridgenet";
	private String ProductClass = "Soft Device";
	private String SerialNumber = "01DA41AA0001";

	public String getOUI() {
		return OUI;
	}

	public void setOUI(String oUI) {
		OUI = oUI;
	}

	public String getManufacturer() {
		return Manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}

	public String getProductClass() {
		return ProductClass;
	}

	public void setProductClass(String productClass) {
		ProductClass = productClass;
	}

	public String getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}

	/**
	 * Forced Inform paremters
	 * 
	 * TR-068 Amendment 2 - 2.4.1 Table 3
	 * 
	 * InternetGatewayDevice.DeviceSummary
	 * InternetGatewayDevice.DeviceInfo.SpecVersion
	 * InternetGatewayDevice.DeviceInfo.HardwareVersion
	 * InternetGatewayDevice.DeviceInfo.SoftwareVersion
	 * InternetGatewayDevice.DeviceInfo.ProvisioningCode
	 * InternetGatewayDevice.ManagementServer.ConnectionRequestURL
	 * InternetGatewayDevice.ManagementServer.ParameterKey
	 * InternetGatewayDevice.WANDevice.{i}.WANConnectionDevice.{j}.WAN{***}Connection.{k}.ExternalIPAddress
	 **/

	/**
	 * TR-106 Amendment 7 Ch. 3.7
	 */
	String DeviceSummary = "InternetGatewayDevice:1.5[](Baseline:1, EthernetLAN:1, Time:1, IPPing:1, "
			+ "DeviceAssociation:1, QoS:1, ADSLWAN:1, ATMLoopback:1, DSLDiagnostics:1, PTMWAN:1, EthernetWAN:1, "
			+ "WiFiLAN:1, Download:1, Upload:1, DownloadTCP:1, UploadTCP:1, UDPEcho:1, UDPEchoPlus:1), "
			+ "VoiceService:1.0[1](Endpoint:1, SIPEndpoint:1)";
	String SpecVersion = "1.0";
	String HardwareVersion = "v1.0.0";
	String SoftwareVersion = "0.0.1";
	String ProvisioningCode = "TCCF";
	String ConnectionRequestURL = "127.0.0.1:7547";
	
	/**
	 * TR-068 Amendment 2 Ch. 2.4 Table 2
	 * 
	 * ParameterKey [String(32)] provides the ACS a reliable and extensible means to
	 * track changes made by the ACS. The value of ParameterKey MUST be equal to the
	 * value of the ParameterKey argument from the most recent successful
	 * SetParameterValues, AddObject, or DeleteObject method call from the ACS.
	 * 
	 * The CPE MUST set ParameterKey to the value specified in the corresponding
	 * method arguments if and only if the method completes successfully and no
	 * fault response is generated. If a method call does not complete successfully
	 * (implying that the changes requested in the method did not take effect), the
	 * value of ParameterKey MUST NOT be modified.
	 * 
	 * The CPE MUST only modify the value of ParameterKey as a result of
	 * SetParameterValues, AddObject, DeleteObject, or due to a factory reset. On
	 * factory reset, the value of ParameterKey MUST be set to empty.
	 * 
	 */
	String ParameterKey = "2344222";
	
	/**
	 * RFC 5737 - IP address for documentation Soft device does not use this for
	 * purpose any other than presentation, so it is applicable to use this range
	 */
	String ExternalIPAddress = "198.51.100.76";

	public String getDeviceSummary() {
		return DeviceSummary;
	}

	public void setDeviceSummary(String deviceSummary) {
		DeviceSummary = deviceSummary;
	}

	public String getSpecVersion() {
		return SpecVersion;
	}

	public void setSpecVersion(String specVersion) {
		SpecVersion = specVersion;
	}

	public String getHardwareVersion() {
		return HardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		HardwareVersion = hardwareVersion;
	}

	public String getSoftwareVersion() {
		return SoftwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		SoftwareVersion = softwareVersion;
	}

	public String getProvisioningCode() {
		return ProvisioningCode;
	}

	public void setProvisioningCode(String provisioningCode) {
		ProvisioningCode = provisioningCode;
	}

	public String getConnectionRequestURL() {
		return ConnectionRequestURL;
	}

	public void setConnectionRequestURL(String connectionRequestURL) {
		ConnectionRequestURL = connectionRequestURL;
	}

	public String getParameterKey() {
		return ParameterKey;
	}

	public void setParameterKey(String parameterKey) {
		ParameterKey = parameterKey;
	}

	public String getExternalIPAddress() {
		return ExternalIPAddress;
	}

	public void setExternalIPAddress(String externalIPAddress) {
		ExternalIPAddress = externalIPAddress;
	}

}
