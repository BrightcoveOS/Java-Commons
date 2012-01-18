package com.brightcove.commons.catalog.objects.enumerations;


/**
 * <p>The status for an uploaded video.</p>
 * 
 * <p>This enumeration is defined primarily by the Media API documentation for
 *    the Video Write API:
 *    <a href="http://docs.brightcove.com/en/media/#Video_Write">http://docs.brightcove.com/en/media/#Video_Write</a><br/>
 *    (specifically the get_upload_status method).
 * </p>
 * <p>The specific values are also defined by the Media API documentation:
 *    <a href="http://docs.brightcove.com/en/media/#UploadStatusEnum">http://docs.brightcove.com/en/media/#UploadStatusEnum</a>:<ul>
 *    	<li>UPLOADING  - File is still uploading</li>
 *    	<li>PROCESSING - Upload complete; being processed</li>
 *    	<li>COMPLETE   - Upload and processing complete</li>
 *    	<li>ERROR      - Error in upload or processing</li>
 *    </ul>
 * </p>
 * <p>This may be modified however from the Media API documentation so that it
 *    can also support other interfaces (e.g. batch provisioning).</p>
 * 
 * @author <a href="https://github.com/three4clavin">three4clavin</a>
 *
 */
public enum UploadStatusEnum {
	UPLOADING, PROCESSING, COMPLETE, ERROR;
}
